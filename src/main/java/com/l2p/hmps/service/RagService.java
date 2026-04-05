package com.l2p.hmps.service;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface RagService {
    
    void ingestText(String content, String title, String docType);
    
    void ingestPdf(MultipartFile file);
    
    List<Document> search(String query, int topK, double threshold);
    
    String getContext(String query, int topK);
    
    void deleteByDocType(String docType);
}