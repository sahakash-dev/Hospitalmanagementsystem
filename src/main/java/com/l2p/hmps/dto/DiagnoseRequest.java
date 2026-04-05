package com.l2p.hmps.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnoseRequest {

    @NotBlank(message = "Symptoms description is required")
    @Size(min = 10, message = "Please provide more detail about symptoms")
    private String symptoms;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 150, message = "Please enter a valid age")
    private int ageYears;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(?i)(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @Size(max = 1000, message = "Existing conditions text is too long")
    private String existingConditions;
}