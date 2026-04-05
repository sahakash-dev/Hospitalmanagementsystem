package com.l2p.hmps.service;

import com.l2p.hmps.dto.CreateMedicalRecordRequest;
import com.l2p.hmps.dto.MedicalRecordResponse;
import com.l2p.hmps.dto.VitalsRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MedicalRecordService {
    MedicalRecordResponse create(CreateMedicalRecordRequest request);
    MedicalRecordResponse getById(UUID id);
    Page<MedicalRecordResponse> getByPatient(UUID patientId, int page, int size);
    Page<MedicalRecordResponse> getByPatientEmail(String email, int page, int size);
    MedicalRecordResponse addVitals(UUID id, VitalsRequest request);
    byte[] exportToPdf(UUID id);
}