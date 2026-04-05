package com.l2p.hmps.controller;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.CreatePrescriptionRequest;
import com.l2p.hmps.dto.PrescriptionResponse;
import com.l2p.hmps.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PrescriptionResponse>> createPrescription(
            @Valid @RequestBody CreatePrescriptionRequest request
    ) {
        PrescriptionResponse response = prescriptionService.create(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Prescription created successfully", response));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByPatient(
            @PathVariable UUID patientId
    ) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Prescriptions fetched successfully", prescriptions));
    }

    @GetMapping("/patient/{patientId}/active")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getActivePrescriptionsByPatient(
            @PathVariable UUID patientId
    ) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getActiveByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Active prescriptions fetched successfully", prescriptions));
    }

    // ✅ NEW
    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByEmail(
            @RequestParam String email
    ) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getByPatientEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Prescriptions fetched successfully", prescriptions));
    }

    // ✅ NEW
    @GetMapping("/by-email/active")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getActivePrescriptionsByEmail(
            @RequestParam String email
    ) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getActiveByPatientEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Active prescriptions fetched successfully", prescriptions));
    }

    @PutMapping("/{id}/fill")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> markPrescriptionFilled(
            @PathVariable UUID id,
            @RequestParam UUID pharmacistId
    ) {
        PrescriptionResponse response = prescriptionService.markFilled(id, pharmacistId);
        return ResponseEntity.ok(ApiResponse.success("Prescription marked as filled", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> revokePrescription(
            @PathVariable UUID id,
            @RequestParam UUID doctorId
    ) {
        prescriptionService.revoke(id, doctorId);
        return ResponseEntity.ok(ApiResponse.success("Prescription revoked successfully", null));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPrescriptionPdf(@PathVariable UUID id) {
        byte[] pdf = prescriptionService.generatePdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("prescription-" + id + ".pdf")
                                .build()
                                .toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}