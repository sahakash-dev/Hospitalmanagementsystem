package com.l2p.hmps.repository;

import com.l2p.hmps.model.Notification;
import com.l2p.hmps.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // 🔹 Used for NotificationBell polling
    long countByUserAndIsReadFalse(User user);

    // 🔹 Used for notifications page (paginated)
    Page<Notification> findByUserOrderBySentAtDesc(User user, Pageable pageable);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId AND n.isRead = false")
    void markAllAsRead(@Param("userId") UUID userId);
}