package com.l2p.hmps.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class DoctorResponse {
    private UUID id;
    private UUID userId;
    private UUID departmentId;
    private String licenseNo;
    private String firstName;
    private String lastName;
    private String specialization;
    private int yearsExperience;
    private BigDecimal rating;
    private String bio;
}