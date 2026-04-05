package com.l2p.hmps.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.DoctorRequest;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.dto.DoctorDashboardResponse;
import com.l2p.hmps.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // ================= REGISTER DOCTOR (ADMIN ONLY) =================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DoctorResponse>> register(@Valid @RequestBody DoctorRequest request) {

        DoctorResponse doctor = doctorService.register(request);

        return new ResponseEntity<>(
                ApiResponse.success("Doctor registered successfully", doctor),
                HttpStatus.CREATED
        );
    }

    // ================= GET DASHBOARD (/me) =================
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<DoctorDashboardResponse>> getDashboard() {

        DoctorDashboardResponse dashboard = doctorService.getDashboard();

        return ResponseEntity.ok(
                ApiResponse.success("Dashboard fetched successfully", dashboard)
        );
    }

    // ================= GET CURRENT DOCTOR PROFILE =================
    @GetMapping("/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<DoctorResponse>> getMyProfile() {

        DoctorResponse doctor = doctorService.getMyProfile();

        return ResponseEntity.ok(
                ApiResponse.success("Doctor fetched successfully", doctor)
        );
    }
}