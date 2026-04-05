package com.l2p.hmps.controller;

import com.l2p.hmps.dto.*;
import com.l2p.hmps.service.DoctorAIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/doctor")
@RequiredArgsConstructor
public class DoctorAIController {

    private final DoctorAIService doctorAIService;

    @PostMapping("/ask")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AIChatResponse>> ask(@Valid @RequestBody AIChatRequest request) {
        AIChatResponse data = doctorAIService.ask(request);
        return ResponseEntity.ok(ApiResponse.success("Doctor AI responded successfully", data));
    }

    @PostMapping("/diagnose")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AIChatResponse>> diagnose(@Valid @RequestBody DiagnoseRequest request) {
        AIChatResponse data = doctorAIService.suggestDiagnosis(request);
        return ResponseEntity.ok(ApiResponse.success("Differential diagnosis generated", data));
    }

    @GetMapping("/summarize/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AIChatResponse>> summarize(@PathVariable UUID patientId) {
        AIChatResponse data = doctorAIService.summarizePatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient summary retrieved", data));
    }
}