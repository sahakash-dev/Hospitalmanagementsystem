package com.l2p.hmps.service;

import com.l2p.hmps.dto.AIChatRequest;
import com.l2p.hmps.dto.AIChatResponse;
import com.l2p.hmps.dto.SymptomCheckRequest;

public interface PatientAIService {
    AIChatResponse chat(AIChatRequest request);
    AIChatResponse checkSymptoms(SymptomCheckRequest request);
}