package com.l2p.hmps.service;

import com.l2p.hmps.model.Notification;
import com.l2p.hmps.model.NotificationType;
import com.l2p.hmps.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    Notification sendInApp(User user, String title, String message, NotificationType type);

    void markAllRead(User user);

    long getUnreadCount(User user);

    Page<Notification> getUserNotifications(User user, Pageable pageable);
}