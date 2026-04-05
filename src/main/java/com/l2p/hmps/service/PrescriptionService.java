package com.l2p.hmps.service;

import com.l2p.hmps.dto.CreatePrescriptionRequest;
import com.l2p.hmps.dto.PrescriptionResponse;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {
    PrescriptionResponse create(CreatePrescriptionRequest request);
    List<PrescriptionResponse> getByPatient(UUID patientId);
    List<PrescriptionResponse> getActiveByPatient(UUID patientId);

    // ✅ NEW
    List<PrescriptionResponse> getByPatientEmail(String email);
    List<PrescriptionResponse> getActiveByPatientEmail(String email);

    void revoke(UUID id, UUID doctorId);
    PrescriptionResponse markFilled(UUID id, UUID pharmacistId);
    byte[] generatePdf(UUID id);
}