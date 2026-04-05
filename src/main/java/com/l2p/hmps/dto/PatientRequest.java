package com.l2p.hmps.dto;

import java.time.LocalDate;
import com.l2p.hmps.model.BloodGroup;
import com.l2p.hmps.model.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientRequest {

    private String nhsId;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;

    @Size(max = 500, message = "Address too long")
    private String address;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid emergency contact")
    private String emergencyContact;

    @Size(max = 255, message = "Insurance info too long")
    private String insuranceInfo;
}