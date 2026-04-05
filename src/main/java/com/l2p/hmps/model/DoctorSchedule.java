package com.l2p.hmps.model;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "doctor_schedules")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Min(30)
    private int slotDurationMinutes = 30;
}