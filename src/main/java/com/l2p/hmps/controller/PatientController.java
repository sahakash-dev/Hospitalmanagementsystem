package com.l2p.hmps.controller;

import java.util.UUID;

import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.PatientRequest;
import com.l2p.hmps.dto.PatientResponse;
import com.l2p.hmps.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> register(@Valid @RequestBody PatientRequest request) {
        PatientResponse patient = patientService.register(request);
        return new ResponseEntity<>(
                ApiResponse.success("Patient registered successfully", patient),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getAll(Pageable pageable) {
        Page<PatientResponse> patients = patientService.getAll(pageable);
        return ResponseEntity.ok(
                ApiResponse.success("Patients fetched successfully", patients)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> search(
            @RequestParam String q,
            Pageable pageable) {

        Page<PatientResponse> result = patientService.search(q, pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Search results fetched", result)
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PatientResponse>> getMyProfile(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        PatientResponse patient = patientService.getByUserId(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Patient profile fetched", patient));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> getById(@PathVariable UUID id) {
        PatientResponse patient = patientService.getByUserId(id);
        return ResponseEntity.ok(ApiResponse.success("Patient fetched successfully", patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public ResponseEntity<ApiResponse<PatientResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody PatientRequest request,
            Authentication authentication) {

        PatientResponse updated = patientService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Patient updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        patientService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Patient deleted successfully", null));
    }
}