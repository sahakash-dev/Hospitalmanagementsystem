package com.l2p.hmps.controller;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.CreateMedicalRecordRequest;
import com.l2p.hmps.dto.MedicalRecordResponse;
import com.l2p.hmps.dto.VitalsRequest;
import com.l2p.hmps.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> createMedicalRecord(
            @Valid @RequestBody CreateMedicalRecordRequest request
    ) {
        MedicalRecordResponse response = medicalRecordService.create(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Medical record created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getMedicalRecordById(@PathVariable UUID id) {
        MedicalRecordResponse response = medicalRecordService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Medical record fetched successfully", response));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<Page<MedicalRecordResponse>>> getMedicalRecordsByPatient(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MedicalRecordResponse> records = medicalRecordService.getByPatient(patientId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Medical records fetched successfully", records));
    }

    // ✅ NEW
    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<Page<MedicalRecordResponse>>> getMedicalRecordsByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MedicalRecordResponse> records = medicalRecordService.getByPatientEmail(email, page, size);
        return ResponseEntity.ok(ApiResponse.success("Medical records fetched successfully", records));
    }

    @PutMapping("/{id}/vitals")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> addVitals(
            @PathVariable UUID id,
            @Valid @RequestBody VitalsRequest request
    ) {
        MedicalRecordResponse response = medicalRecordService.addVitals(id, request);
        return ResponseEntity.ok(ApiResponse.success("Vitals updated successfully", response));
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportMedicalRecordPdf(@PathVariable UUID id) {
        byte[] pdf = medicalRecordService.exportToPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("medical-record-" + id + ".pdf")
                                .build()
                                .toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}