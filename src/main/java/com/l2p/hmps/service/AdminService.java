package com.l2p.hmps.service;

import com.l2p.hmps.dto.AdminStatsResponse;
import com.l2p.hmps.dto.UserResponse;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface AdminService {

    AdminStatsResponse getDashboard();

    Page<UserResponse> getAllUsers(Pageable pageable);

    void activateUser(UUID userId);

    void deactivateUser(UUID userId);
}