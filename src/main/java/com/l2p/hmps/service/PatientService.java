package com.l2p.hmps.service;

import java.util.UUID;

import com.l2p.hmps.dto.PatientRequest;
import com.l2p.hmps.dto.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponse register(PatientRequest request);
    PatientResponse getByUserId(UUID userId);
    Page<PatientResponse> getAll(Pageable pageable);
    Page<PatientResponse> search(String q, Pageable pageable);
    PatientResponse update(UUID id, PatientRequest request);

    // ================= UPDATE =================
    PatientResponse update(PatientRequest request);

    void delete(UUID id);
}
