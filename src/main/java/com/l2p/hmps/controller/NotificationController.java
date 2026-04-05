package com.l2p.hmps.controller;

import com.l2p.hmps.dto.NotificationResponse;
import com.l2p.hmps.mapper.NotificationMapper;
import com.l2p.hmps.model.Notification;
import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.UserRepository;
import com.l2p.hmps.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    // 🔹 Get notifications (paginated)
    @GetMapping
    public Page<NotificationResponse> getNotifications(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page
    ) {

        User user = getUserIdFromAuth(authentication);

        Pageable pageable = PageRequest.of(page, 10);

        Page<Notification> notifications =
                notificationService.getUserNotifications(user, pageable);

        return notifications.map(notificationMapper::toDTO);
    }

    // 🔹 Mark all as read
    @PutMapping("/read-all")
    public void markAllRead(Authentication authentication) {

        User user = getUserIdFromAuth(authentication);

        notificationService.markAllRead(user);
    }

    // 🔹 Get unread count
    @GetMapping("/unread-count")
    public long getUnreadCount(Authentication authentication) {

        User user = getUserIdFromAuth(authentication);

        return notificationService.getUnreadCount(user);
    }

    // 🔥 Helper (VERY IMPORTANT)
       // assuming principal stores userId
    private User getUserIdFromAuth(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication is missing or invalid");
        }

        String email = authentication.getName();



        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found" + email));


    }
    }