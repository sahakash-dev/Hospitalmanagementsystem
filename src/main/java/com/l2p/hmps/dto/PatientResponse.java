package com.l2p.hmps.dto;

import java.time.LocalDate;
import java.util.UUID;
import com.l2p.hmps.model.BloodGroup;
import com.l2p.hmps.model.Gender;
import lombok.Data;

@Data
public class PatientResponse {
    private UUID id;
    private UUID userId;
    private String nhsId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodGroup bloodGroup;
    private String phone;
    private String address;
    private String emergencyContact;
    private String insuranceInfo;
}