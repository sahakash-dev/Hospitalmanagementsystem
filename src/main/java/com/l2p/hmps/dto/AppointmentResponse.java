package com.l2p.hmps.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AppointmentResponse {

    private UUID appointmentId;

    private UUID patientId;
    private String patientName;

    private UUID doctorId;
    private String doctorName;
    private String doctorSpecialization;

    private LocalDate appointmentDate;
    private String slot;           // e.g. "09:00"

    private String type;           // IN_PERSON | VIDEO | PHONE
    private String status;         // PENDING | CONFIRMED | CANCELLED | COMPLETED

    private String reason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}