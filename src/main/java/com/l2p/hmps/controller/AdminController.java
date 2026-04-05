package com.l2p.hmps.controller;

import com.l2p.hmps.dto.AdminStatsResponse;
import com.l2p.hmps.service.AdminService;
import com.l2p.hmps.dto.UserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 🔹 Get all users (paginated)
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return adminService.getAllUsers(pageable);
    }

    // 🔹 Dashboard stats
    @GetMapping("/stats/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminStatsResponse getDashboard() {
        return adminService.getDashboard();
    }

    // 🔹 Activate user
    @PutMapping("/users/{id}/activate")
    public void activateUser(@PathVariable UUID id) {
        adminService.activateUser(id);
    }

    // 🔹 Deactivate user
    @PutMapping("/users/{id}/deactivate")
    public void deactivateUser(@PathVariable UUID id) {
        adminService.deactivateUser(id);
    }
}