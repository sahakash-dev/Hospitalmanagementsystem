package com.l2p.hmps.controller;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    @PostMapping("/ingest")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> ingest(@RequestParam("file") MultipartFile file) {
        ragService.ingestPdf(file);
        return ResponseEntity.ok(ApiResponse.success("PDF ingested into vector store", file.getOriginalFilename()));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<String>>> search(
            @RequestParam String query, 
            @RequestParam(defaultValue = "5") int topK) {
        
        var docs = ragService.search(query, topK, 0.65);
        List<String> contentList = docs.stream().map(d -> d.getText()).collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success("Similarity search results", contentList));
    }

    @DeleteMapping("/clear/{docType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteDocs(@PathVariable String docType) {
        ragService.deleteByDocType(docType);
        return ResponseEntity.ok(ApiResponse.success("Documents of type " + docType + " deleted", null));
    }
}