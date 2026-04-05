package com.l2p.hmps.service;

import com.l2p.hmps.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientAIServiceImpl implements PatientAIService {

    @Qualifier("patientChatClient")
    private final ChatClient patientChatClient;
    
    private final RagService ragService;

    @Value("classpath:/prompts/patient-symptoms.st")
    private Resource patientPromptResource;

    private static final List<String> BLOCKED_KEYWORDS = List.of(
            "suicide", "overdose", "illegal drugs", "kill myself", "end my life"
    );

    @Override
    public AIChatResponse chat(AIChatRequest request) {
        log.info("Patient AI: Processing general health query");
        
        // 1. Safety Filter Check
        String messageLower = request.getMessage().toLowerCase();
        for (String word : BLOCKED_KEYWORDS) {
            if (messageLower.contains(word)) {
                log.warn("Safety trigger: Blocked keyword detected");
                return AIChatResponse.of(
                    "I am very concerned about what you are saying. Please reach out to emergency services or a crisis helpline (like 112) immediately. You are not alone.", 
                    0
                );
            }
        }

        String context = ragService.getContext(request.getMessage(), 3);
        
        String answer = patientChatClient.prompt()
                .user(u -> u.text("Medical Context:\n{context}\n\nUser Question: {query}")
                        .param("context", context)
                        .param("query", request.getMessage()))
                .call()
                .content();

        return AIChatResponse.of(answer, 3);
    }

    @Override
    public AIChatResponse checkSymptoms(SymptomCheckRequest request) {
        log.info("Patient AI: Checking symptoms for simple guidance");
        
        String symptomsJoined = String.join(", ", request.getSymptoms());
        
        String context = ragService.getContext(symptomsJoined, 3);

        String answer = patientChatClient.prompt()
                .user(u -> u.text(patientPromptResource)
                        .param("symptoms", symptomsJoined)
                        .param("context", (context.isEmpty()) ? "General health guidelines" : context))
                .call()
                .content();

        return AIChatResponse.of(answer, 3);
    }
}