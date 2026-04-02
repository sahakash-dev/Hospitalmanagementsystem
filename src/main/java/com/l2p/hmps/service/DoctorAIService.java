package com.l2p.hmps.service;

import java.util.UUID;

import com.l2p.hmps.dto.AIChatRequest;
import com.l2p.hmps.dto.AIChatResponse;
import com.l2p.hmps.dto.DiagnoseRequest;

public interface DoctorAIService {
    AIChatResponse ask(AIChatRequest request);
    AIChatResponse suggestDiagnosis(DiagnoseRequest request);
    AIChatResponse summarizePatient(UUID patientId);
}