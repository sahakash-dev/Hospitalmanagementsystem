package com.l2p.hmps.dto;

import java.time.LocalTime;
import java.util.UUID;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleRequest {

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;

    @NotNull(message = "Day of week is required")
    private String dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @Min(value = 30, message = "Slot duration must be at least 30 minutes")
    private int slotDurationMinutes;
}
