package com.l2p.hmps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIChatResponse {
    
    private String answer;
    private int sourcesUsed;
    private LocalDateTime timestamp;

    // Helper to auto-set timestamp
    public static AIChatResponse of(String answer, int sourcesUsed) {
        return AIChatResponse.builder()
                .answer(answer)
                .sourcesUsed(sourcesUsed)
                .timestamp(LocalDateTime.now())
                .build();
    }
}