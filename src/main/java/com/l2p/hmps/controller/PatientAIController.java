package com.l2p.hmps.controller;

import com.l2p.hmps.dto.*;
import com.l2p.hmps.service.PatientAIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/patient")
@RequiredArgsConstructor
public class PatientAIController {

    private final PatientAIService patientAIService;

    @PostMapping("/chat")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<AIChatResponse>> chat(@Valid @RequestBody AIChatRequest request) {
        AIChatResponse data = patientAIService.chat(request);
        return ResponseEntity.ok(ApiResponse.success("Health assistant responded", data));
    }

    @PostMapping("/symptoms")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<AIChatResponse>> checkSymptoms(@Valid @RequestBody SymptomCheckRequest request) {
        AIChatResponse data = patientAIService.checkSymptoms(request);
        return ResponseEntity.ok(ApiResponse.success("Symptom analysis complete", data));
    }
}