package com.l2p.hmps.service;

import com.l2p.hmps.dto.AdminStatsResponse;
import com.l2p.hmps.dto.UserResponse;
import com.l2p.hmps.model.AuditLog;
import com.l2p.hmps.model.Role;
import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    // 🔴 TEMP DISABLED (waiting for teammate implementation)
    // private final BillRepository billRepository;
    // private final LabResultRepository labResultRepository;

    private final AuditLogRepository auditLogRepository;

    @Override
    public AdminStatsResponse getDashboard() {

        long totalPatients = userRepository.countByRole(Role.PATIENT);
        long totalDoctors = userRepository.countByRole(Role.DOCTOR);
//        long todayAppointments = appointmentRepository.countByDate(LocalDate.now());
        long todayAppointments = getTodayAppointmentsFallback();

        // 🔴 TEMP MOCK VALUES (replace when repo ready)
        long pendingBills = getPendingBillsFallback();
        long criticalLabs = getCriticalLabsFallback();

        return AdminStatsResponse.builder()
                .totalPatients(totalPatients)
                .totalDoctors(totalDoctors)
                .todayAppointments(todayAppointments)
                .pendingBills(pendingBills)
                .unverifiedCriticalLabs(criticalLabs)
                .build();
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRole(),
                        user.isActive()
                ));
    }

    private long getTodayAppointmentsFallback() {
        // 🔴 Replace later:
        // return appointmentRepository.countByAppointmentDate(LocalDate.now());

        return 0; // TEMP SAFE VALUE
    }

    // 🔥 TEMP METHODS (clearly marked)
    private long getPendingBillsFallback() {
        // TODO: replace with billRepository.countByStatus("PENDING")
        return 0;
    }

    private long getCriticalLabsFallback() {
        // TODO: replace with labResultRepository.countByIsCriticalTrueAndVerifiedFalse()
        return 0;
    }

    @Override
    @Transactional
    public void activateUser(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean oldStatus = user.isActive();

        user.setActive(true);
        userRepository.save(user);

        logAudit(userId, "ACTIVATE_USER", "User", userId,
                String.valueOf(oldStatus),
                String.valueOf(true));
    }

    @Override
    @Transactional
    public void deactivateUser(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean oldStatus = user.isActive();

        user.setActive(false);
        userRepository.save(user);

        logAudit(userId, "DEACTIVATE_USER", "User", userId,
                String.valueOf(oldStatus),
                String.valueOf(false));
    }

    private void logAudit(UUID performedBy, String action,
                          String entityType, UUID entityId,
                          String oldValue, String newValue) {

        User performedByUser = userRepository.findById(performedBy)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuditLog log = AuditLog.builder()
                .user(performedByUser)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .build();

        auditLogRepository.save(log);
    }
}