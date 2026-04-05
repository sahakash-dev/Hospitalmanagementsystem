package com.l2p.hmps.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatsResponse {

    private long totalPatients;
    private long totalDoctors;
    private long todayAppointments;
    private long pendingBills;
    private long unverifiedCriticalLabs;
}