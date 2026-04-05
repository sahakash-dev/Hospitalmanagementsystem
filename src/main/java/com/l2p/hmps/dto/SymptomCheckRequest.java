package com.l2p.hmps.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymptomCheckRequest {

    @NotEmpty(message = "Symptom list cannot be empty")
    private List<String> symptoms;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 120, message = "Invalid age")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;
}