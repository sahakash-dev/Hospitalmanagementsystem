package com.l2p.hmps.service;

import com.l2p.hmps.model.Notification;
import com.l2p.hmps.model.NotificationType;
import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.NotificationRepository;
import com.l2p.hmps.repository.UserRepository;
import com.l2p.hmps.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public Notification sendInApp(User user, String title, String message, NotificationType type) {

//        User user = userRepository.findById(user.getId())
//                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .build();

        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllRead(User user) {

        notificationRepository.markAllAsRead(user.getId());
    }

    @Override
    public long getUnreadCount(User user) {

        return notificationRepository.countByUserAndIsReadFalse(user);
    }

    @Override
    public Page<Notification> getUserNotifications(User user, Pageable pageable) {

        return notificationRepository.findByUserOrderBySentAtDesc(user, pageable);
    }
}