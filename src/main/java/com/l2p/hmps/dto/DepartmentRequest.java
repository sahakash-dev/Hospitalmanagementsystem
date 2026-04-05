package com.l2p.hmps.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 150, message = "Department name too long")
    private String name;

    @Size(max = 500, message = "Description too long")
    private String description;

    private UUID headDoctorId;
}