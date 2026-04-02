package com.l2p.hmps.service;

import com.l2p.hmps.exception.AIServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

    private final VectorStore vectorStore;

    @Override
    public void ingestText(String content, String title, String docType) {
        try {
            Document doc = new Document(content, Map.of(
                    "title", title,
                    "docType", docType
            ));
            
            TokenTextSplitter splitter = new TokenTextSplitter(512, 50, 5, 100, true);
            vectorStore.add(splitter.apply(List.of(doc)));
            
            log.info("Ingested text: {}", title);
        } catch (Exception e) {
            throw new AIServiceException("Error ingesting text: " + e.getMessage());
        }
    }

    @Override
    public void ingestPdf(MultipartFile file) {
        try {
            PagePdfDocumentReader reader = new PagePdfDocumentReader(file.getResource());
            TokenTextSplitter splitter = new TokenTextSplitter(512, 50, 5, 100, true);
            
            vectorStore.add(splitter.apply(reader.get()));
            log.info("Ingested PDF: {}", file.getOriginalFilename());
        } catch (Exception e) {
            throw new AIServiceException("Error ingesting PDF: " + e.getMessage());
        }
    }

    @Override
    public List<Document> search(String query, int topK, double threshold) {
        try {
            SearchRequest request = SearchRequest.builder()
                    .query(query)
                    .topK(topK)
                    .similarityThreshold(threshold)
                    .build();

            return vectorStore.similaritySearch(request);
        } catch (Exception e) {
            log.error("Vector search failed: {}", e.getMessage());
            throw new AIServiceException("AI search failed to retrieve context", 
                                       HttpStatus.SERVICE_UNAVAILABLE, "VECTOR_DB_SEARCH_ERROR");
        }
    }

    @Override
    public String getContext(String query, int topK) {
        List<Document> results = search(query, topK, 0.65);
        if (results.isEmpty()) {
            return "";
        }

        return results.stream()
                .map(doc -> {
                    Object titleObj = doc.getMetadata().get("title");
                    String title = (titleObj != null) ? titleObj.toString() : "Reference";
                    
                    return String.format("[%s]: %s", title, doc.getText()); 
                })
                .collect(Collectors.joining("\n---\n"));
    }

    @Override
    public void deleteByDocType(String docType) {
        try {
            FilterExpressionBuilder b = new FilterExpressionBuilder();
            vectorStore.delete(b.eq("docType", docType).build());
            log.info("Deleted documents for type: {}", docType);
        } catch (Exception e) {
            throw new AIServiceException("Delete failed: " + e.getMessage());
        }
    }
}