package com.l2p.hmps.service;

import com.l2p.hmps.dto.AppointmentResponse;
import com.l2p.hmps.dto.BookAppointmentRequest;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.AppointmentStatus;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Appointment bookAppointment(BookAppointmentRequest request);

    Appointment getAppointmentById(UUID appointmentId);

    List<Appointment> getAppointmentsByPatient(UUID patientId);

    List<Appointment> getAppointmentsByDoctor(UUID doctorId);

    // ✅ NEW
    List<Appointment> getAppointmentsByEmail(String email);

    Appointment cancelAppointment(UUID appointmentId);

    Appointment updateAppointmentStatus(UUID appointmentId, AppointmentStatus status);


    // 🔹 DTO METHODS (API Layer)

    AppointmentResponse create(BookAppointmentRequest request);

    AppointmentResponse getById(UUID id);
}