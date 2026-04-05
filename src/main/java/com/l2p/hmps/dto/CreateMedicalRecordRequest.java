package com.l2p.hmps.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateMedicalRecordRequest {

    @NotNull(message = "Patient id is required")
    private UUID patientId;

    @NotNull(message = "Doctor id is required")
    private UUID doctorId;

    private UUID appointmentId;

    @Future(message = "Follow-up date must be in the future")
    private LocalDate visitDate;

    @Size(max = 1000, message = "Chief complaint cannot exceed 1000 characters")
    private String chiefComplaint;

    @Size(max = 2000, message = "Diagnosis cannot exceed 2000 characters")
    private String diagnosis;

    @Size(max = 20, message = "ICD-10 code cannot exceed 20 characters")
    private String icd10Code;

    @Size(max = 2000, message = "Symptoms cannot exceed 2000 characters")
    private String symptoms;

    @Size(max = 2000, message = "Treatment plan cannot exceed 2000 characters")
    private String treatmentPlan;

    @PastOrPresent(message = "Follow-up date cannot be in the future")
    private LocalDate followUpDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    private Double weight;

    @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than 0")
    private Double height;

    @DecimalMin(value = "0.0", inclusive = false, message = "Temperature must be greater than 0")
    private Double temperature;

    @Pattern(regexp = "^\\d{2,3}/\\d{2,3}$", message = "Blood pressure must be in format 120/80")
    private String bloodPressure;

    @Min(value = 20, message = "Heart rate must be at least 20")
    @Max(value = 300, message = "Heart rate must not exceed 300")
    private Integer heartRate;

    @Min(value = 50, message = "SpO2 must be at least 50")
    @Max(value = 100, message = "SpO2 must not exceed 100")
    private Integer spo2;
}