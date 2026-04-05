package com.l2p.hmps.repository;

import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.AppointmentStatus;
import com.l2p.hmps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // 🔹 Get all appointments for patient
    List<Appointment> findByPatient(User patient);

    // 🔹 Get all appointments for doctor
    List<Appointment> findByDoctor(User doctor);

    // 🔹 Get appointments by doctor + date (for schedule view)
    List<Appointment> findByDoctorAndAppointmentDate(User doctor, LocalDate appointmentDate);

    // 🔹 Check if slot already exists (conflict check)
    boolean existsByDoctorAndAppointmentDateAndSlotAndStatusNot(
            User doctor,
            LocalDate appointmentDate,
            String slot,
            AppointmentStatus status
    );

    // 🔹 Validate appointment belongs to doctor
    boolean existsByIdAndDoctorId(UUID appointmentId, UUID doctorId);

    // 🔹 Get appointment by id + doctor (secure fetch)
    Optional<Appointment> findByIdAndDoctorId(UUID appointmentId, UUID doctorId);
}