package com.l2p.hmps.controller;

import java.util.List;
import java.util.UUID;

import com.l2p.hmps.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.DepartmentRequest;
import com.l2p.hmps.dto.DepartmentResponse;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    // ================= CREATE DEPARTMENT =================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> create(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse dept = departmentService.create(request);

        return new ResponseEntity<>(
                ApiResponse.success("Department created successfully", dept),
                HttpStatus.CREATED
        );
    }

    // ================= GET DOCTORS BY DEPARTMENT =================
    @GetMapping("/{id}/doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByDepartment(
            @PathVariable UUID id) {

        List<DoctorResponse> doctors = doctorService.getDoctorsByDepartment(id);

        return ResponseEntity.ok(
                ApiResponse.success("Department doctors fetched successfully", doctors)
        );
    }

    // ================= ASSIGN HEAD DOCTOR =================
    @PutMapping("/{id}/assign-head")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> assignHeadDoctor(
            @PathVariable UUID id,
            @RequestParam UUID doctorId) {

        DepartmentResponse department = departmentService.assignHeadDoctor(id, doctorId);

        return ResponseEntity.ok(
                ApiResponse.success("Head doctor assigned successfully", department)
        );
    }

    // ================= GET DEPARTMENT BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getById(@PathVariable UUID id) {

        DepartmentResponse dept = departmentService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Department fetched successfully", dept)
        );
    }
}