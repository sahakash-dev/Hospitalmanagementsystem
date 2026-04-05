package com.l2p.hmps.controller;

import com.l2p.hmps.dto.AppointmentResponse;
import com.l2p.hmps.dto.BookAppointmentRequest;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.AppointmentStatus;
import com.l2p.hmps.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;  // ✅ NEW import
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // 🔹 DTO BASED (BEST PRACTICE)
    @PostMapping
    public AppointmentResponse create(@Valid @RequestBody BookAppointmentRequest request) {
        return appointmentService.create(request);
    }

    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable UUID id) {
        return appointmentService.getById(id);
    }

    // 🔹 ENTITY BASED (if needed)
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable UUID patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable UUID doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    // ✅ NEW
    @GetMapping("/by-email")
    public ResponseEntity<List<Appointment>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByEmail(email));
    }

    @PutMapping("/{id}/cancel")
    public Appointment cancel(@PathVariable UUID id) {
        return appointmentService.cancelAppointment(id);
    }

    @PutMapping("/{id}/status")
    public Appointment updateStatus(
            @PathVariable UUID id,
            @RequestParam AppointmentStatus status
    ) {
        return appointmentService.updateAppointmentStatus(id, status);
    }
}