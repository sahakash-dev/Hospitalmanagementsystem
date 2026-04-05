package com.l2p.hmps.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.l2p.hmps.dto.ScheduleRequest;
import com.l2p.hmps.dto.SlotResponse;
import com.l2p.hmps.exception.DoctorException;
import com.l2p.hmps.mapper.DoctorScheduleMapper;
import com.l2p.hmps.model.Doctor;
import com.l2p.hmps.model.DoctorSchedule;
import com.l2p.hmps.repository.DoctorRepository;
import com.l2p.hmps.repository.DoctorScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImp implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleMapper scheduleMapper;

    // ================= GET CURRENT DOCTOR =================
    private Doctor getCurrentDoctor() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return doctorRepository.findByUser_Email(email)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"
                ));
    }

    // ================= GET AVAILABLE SLOTS =================
    @Override
    public List<SlotResponse> getAvailableSlots(LocalDate date) {

        Doctor doctor = getCurrentDoctor();

        List<DoctorSchedule> schedules =
                doctorScheduleRepository.findByDoctor_Id(doctor.getId());

        return schedules.stream()
                .map(s -> new SlotResponse(s.getStartTime(), true))
                .collect(Collectors.toList());
    }

    // ================= SET SCHEDULE =================
    @Override
    @Transactional
    public void setSchedule(List<ScheduleRequest> schedules) {

        Doctor doctor = getCurrentDoctor();

        doctorScheduleRepository.deleteByDoctor_Id(doctor.getId());

        List<DoctorSchedule> newSchedules = schedules.stream().map(req -> {
            DoctorSchedule entity = scheduleMapper.toEntity(req);
            entity.setDoctor(doctor);
            return entity;
        }).collect(Collectors.toList());

        doctorScheduleRepository.saveAll(newSchedules);
    }
}