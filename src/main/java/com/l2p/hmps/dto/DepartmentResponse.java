package com.l2p.hmps.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class DepartmentResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID headDoctorId;
}