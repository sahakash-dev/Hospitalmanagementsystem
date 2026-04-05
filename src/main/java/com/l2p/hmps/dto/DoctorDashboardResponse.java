package com.l2p.hmps.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DoctorDashboardResponse {
    private List<BookAppointmentRequest> upcomingAppointments;
    private Long pendingRecordsCount;
    private Long totalPatientsCount;
    private BigDecimal averageRating;
}