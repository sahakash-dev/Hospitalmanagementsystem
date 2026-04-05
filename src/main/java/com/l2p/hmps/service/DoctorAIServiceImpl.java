package com.l2p.hmps.service;

import com.l2p.hmps.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorAIServiceImpl implements DoctorAIService {

    @Qualifier("doctorChatClient")
    private final ChatClient doctorChatClient;
    
    private final RagService ragService;

    @Value("classpath:/prompts/doctor-diagnose.st")
    private Resource diagnosePromptResource;

    @Override
    public AIChatResponse ask(AIChatRequest request) {
        log.info("Doctor AI: Processing general clinical query");
        String context = ragService.getContext(request.getMessage(), 5);
        
        String answer = doctorChatClient.prompt()
                .user(u -> u.text("Context:\n{context}\n\nQuestion: {query}")
                        .param("context", context)
                        .param("query", request.getMessage()))
                .call()
                .content();

        return AIChatResponse.of(answer, 5);
    }

    @Override
    public AIChatResponse suggestDiagnosis(DiagnoseRequest request) {
        log.info("Doctor AI: Generating differential diagnosis for symptoms: {}", request.getSymptoms());

        String context = ragService.getContext(request.getSymptoms(), 4);

        String answer = doctorChatClient.prompt()
                .user(u -> u.text(diagnosePromptResource)
                        .param("symptoms", request.getSymptoms())
                        .param("age", request.getAgeYears())
                        .param("gender", request.getGender())
                        .param("history", (request.getExistingConditions() != null) ? request.getExistingConditions() : "None reported")
                        .param("context", context))
                .call()
                .content();

        return AIChatResponse.of(answer, 4);
    }

    @Override
    public AIChatResponse summarizePatient(UUID patientId) {
        log.info("Doctor AI: Summarizing history for patient ID: {}", patientId);
        
        String mockHistory = "Patient has history of Hypertension and Type 2 Diabetes."; 
        
        String answer = doctorChatClient.prompt()
                .user("Summarize this patient history in 3 bullet points: " + mockHistory)
                .call()
                .content();

        return AIChatResponse.of(answer, 0);
    }
}