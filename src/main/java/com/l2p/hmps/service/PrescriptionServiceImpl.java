package com.l2p.hmps.service;

import com.l2p.hmps.dto.CreatePrescriptionRequest;
import com.l2p.hmps.dto.PrescriptionResponse;
import com.l2p.hmps.exception.PrescriptionException;
import com.l2p.hmps.mapper.PrescriptionMapper;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.Prescription;
import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.AppointmentRepository;
import com.l2p.hmps.repository.PrescriptionRepository;
import com.l2p.hmps.repository.UserRepository;
import com.l2p.hmps.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional
    public PrescriptionResponse create(CreatePrescriptionRequest request) {
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PrescriptionException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new PrescriptionException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new PrescriptionException(
                            "Appointment not found",
                            HttpStatus.NOT_FOUND,
                            "RX_404"
                    ));
        }

        // Map request to entity
        Prescription prescription = prescriptionMapper.toEntity(request);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setAppointment(appointment);
        prescription.setActive(true);
        prescription.setFilled(false);

        // Save to DB
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(savedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getByPatient(UUID patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new PrescriptionException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        return prescriptionRepository.findByPatient(patient)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getActiveByPatient(UUID patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new PrescriptionException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        return prescriptionRepository.findByPatientAndIsActiveTrue(patient)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getByPatientEmail(String email) {
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new PrescriptionException(
                        "Patient not found with email: " + email,
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        return prescriptionRepository.findByPatient(patient)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getActiveByPatientEmail(String email) {
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new PrescriptionException(
                        "Patient not found with email: " + email,
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        return prescriptionRepository.findByPatientAndIsActiveTrue(patient)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void revoke(UUID id, UUID doctorId) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionException(
                        "Prescription not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        if (prescription.getDoctor() == null || !prescription.getDoctor().getId().equals(doctorId)) {
            throw new PrescriptionException(
                    "You are not allowed to revoke this prescription",
                    HttpStatus.FORBIDDEN,
                    "RX_403"
            );
        }

        prescription.setActive(false);
        prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional
    public PrescriptionResponse markFilled(UUID id, UUID pharmacistId) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionException(
                        "Prescription not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        prescription.setFilled(true);
        prescription.setFilledBy(pharmacistId);
        prescription.setFilledAt(LocalDateTime.now());

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(updatedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generatePdf(UUID id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionException(
                        "Prescription not found",
                        HttpStatus.NOT_FOUND,
                        "RX_404"
                ));

        // Generate PDF using PdfGenerator utility
        return PdfGenerator.generatePrescriptionPdf(
                prescription,
                prescription.getPatient(),
                prescription.getDoctor()
        );
    }
}