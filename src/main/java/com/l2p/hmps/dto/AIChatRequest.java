package com.l2p.hmps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIChatRequest {

    @NotBlank(message = "Message is required")
    @Size(min = 2, max = 2000, message = "Message must be between 2 and 2000 characters")
    private String message;
}