package com.l2p.hmps.dto;

import com.l2p.hmps.model.NotificationType;
import com.l2p.hmps.model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private UUID id;
//    private UUID userid;
    private String title;
    private String message;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime sentAt;
}