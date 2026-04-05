package com.l2p.hmps.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookAppointmentRequest {

    @NotNull(message = "Patient id is required")
    private UUID patientId;

    @NotNull(message = "Doctor id is required")
    private UUID doctorId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotBlank(message = "Slot is required")
    private String slot; // e.g. "09:00"

    @NotBlank(message = "Appointment type is required")
    private String type; // IN_PERSON | VIDEO | PHONE

    @Size(max = 1000, message = "Reason cannot exceed 1000 characters")
    private String reason;
}