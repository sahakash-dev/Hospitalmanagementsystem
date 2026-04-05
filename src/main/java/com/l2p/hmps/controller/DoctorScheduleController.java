package com.l2p.hmps.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.l2p.hmps.dto.ApiResponse;
import com.l2p.hmps.dto.ScheduleRequest;
import com.l2p.hmps.dto.SlotResponse;
import com.l2p.hmps.service.DoctorScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    // ================= GET AVAILABLE SLOTS (/me) =================
    @GetMapping("/slots")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<SlotResponse>>> getSlots(
            @RequestParam LocalDate date) {

        List<SlotResponse> slots = doctorScheduleService.getAvailableSlots(date);

        return ResponseEntity.ok(
                ApiResponse.success("Available slots fetched successfully", slots)
        );
    }

    // ================= SET SCHEDULE (/me) =================
    @PutMapping("/config")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<String>> setSchedule(
            @Valid @RequestBody List<ScheduleRequest> schedules) {

        doctorScheduleService.setSchedule(schedules);

        return ResponseEntity.ok(
                ApiResponse.success("Doctor schedule updated successfully", null)
        );
    }
}