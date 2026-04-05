package com.l2p.hmps.dto;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

    @NotBlank(message = "License number is required")
    private String licenseNo;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Specialization is required")
    @Size(max = 200, message = "Specialization too long")
    private String specialization;

    @Min(value = 0, message = "Experience cannot be negative")
    @NotNull(message = "years of experience is required")
    private int yearsExperience;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5")
    private BigDecimal rating;

    @Size(max = 1000, message = "Bio too long")
    private String bio;
}