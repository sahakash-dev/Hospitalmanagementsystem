package com.l2p.hmps.service;

import java.time.LocalDate;
import java.util.List;

import com.l2p.hmps.dto.ScheduleRequest;
import com.l2p.hmps.dto.SlotResponse;

public interface DoctorScheduleService {

    List<SlotResponse> getAvailableSlots(LocalDate date);

    void setSchedule(List<ScheduleRequest> schedules);
}