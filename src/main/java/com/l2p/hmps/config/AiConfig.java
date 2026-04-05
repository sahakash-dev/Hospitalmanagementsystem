package com.l2p.hmps.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {
	
    @Value("classpath:/prompts/system-doctor.st")
    private Resource doctorSystemPrompt;

    @Value("classpath:/prompts/system-patient.st")
    private Resource patientSystemPrompt;

    @Bean
    public ChatClient doctorChatClient(@Qualifier("googleGenAiChatModel") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(doctorSystemPrompt) 
                .build();
    }

    @Bean
    public ChatClient patientChatClient(@Qualifier("googleGenAiChatModel") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem(patientSystemPrompt)
                .build();
    }
}