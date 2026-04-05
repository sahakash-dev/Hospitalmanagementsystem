package com.l2p.hmps.service;

import com.l2p.hmps.dto.AppointmentResponse;
import com.l2p.hmps.dto.BookAppointmentRequest;
import com.l2p.hmps.exception.AppointmentException;
import com.l2p.hmps.mapper.AppointmentMapper;
import com.l2p.hmps.model.*;
import com.l2p.hmps.repository.AppointmentRepository;
import com.l2p.hmps.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    // 🔹 ENTITY METHODS

    @Override
    public Appointment bookAppointment(BookAppointmentRequest request) {

        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new AppointmentException("Patient not found", HttpStatus.NOT_FOUND, "APT_404"));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new AppointmentException("Doctor not found", HttpStatus.NOT_FOUND, "APT_404"));

        if (appointmentRepository.existsByDoctorAndAppointmentDateAndSlotAndStatusNot(
                doctor,
                request.getAppointmentDate(),
                request.getSlot(),
                AppointmentStatus.CANCELLED
        )) {
            throw new AppointmentException("Slot already booked", HttpStatus.BAD_REQUEST, "APT_400");
        }

        Appointment appointment = appointmentMapper.toEntity(request);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(LocalTime.parse(request.getSlot().trim()));
        appointment.setType(AppointmentType.valueOf(request.getType().toUpperCase()));

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentException("Appointment not found", HttpStatus.NOT_FOUND, "APT_404"));
    }

    // ✅ NEW — Find user by email, then fetch their appointments
    @Override
    public List<Appointment> getAppointmentsByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppointmentException("User not found with email: " + email, HttpStatus.NOT_FOUND, "APT_404"));

        return appointmentRepository.findByPatient(user);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(UUID patientId) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new AppointmentException("Patient not found", HttpStatus.NOT_FOUND, "APT_404"));

        return appointmentRepository.findByPatient(patient);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(UUID doctorId) {

        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new AppointmentException("Doctor not found", HttpStatus.NOT_FOUND, "APT_404"));

        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public Appointment cancelAppointment(UUID appointmentId) {

        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(AppointmentStatus.CANCELLED);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointmentStatus(UUID appointmentId, AppointmentStatus status) {

        Appointment appointment = getAppointmentById(appointmentId);
        appointment.setStatus(status);

        return appointmentRepository.save(appointment);
    }

    // 🔹 DTO METHODS (API)

    @Override
    public AppointmentResponse create(BookAppointmentRequest request) {
        return appointmentMapper.toResponse(bookAppointment(request));
    }

    @Override
    public AppointmentResponse getById(UUID id) {
        return appointmentMapper.toResponse(getAppointmentById(id));
    }
}