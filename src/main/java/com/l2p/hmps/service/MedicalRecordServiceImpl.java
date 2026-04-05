package com.l2p.hmps.service;

import com.l2p.hmps.dto.CreateMedicalRecordRequest;
import com.l2p.hmps.dto.MedicalRecordResponse;
import com.l2p.hmps.dto.VitalsRequest;
import com.l2p.hmps.exception.MedicalRecordException;
import com.l2p.hmps.mapper.MedicalRecordMapper;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.MedicalRecord;
import com.l2p.hmps.model.User;
import com.l2p.hmps.repository.AppointmentRepository;
import com.l2p.hmps.repository.MedicalRecordRepository;
import com.l2p.hmps.repository.UserRepository;
import com.l2p.hmps.util.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public MedicalRecordResponse create(CreateMedicalRecordRequest request) {
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new MedicalRecordException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new MedicalRecordException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        Appointment appointment = null;
        if (request.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new MedicalRecordException(
                            "Appointment not found",
                            HttpStatus.NOT_FOUND,
                            "MR_404"
                    ));

            if (medicalRecordRepository.existsByAppointment(appointment)) {
                throw new MedicalRecordException(
                        "Medical record already exists for this appointment",
                        HttpStatus.BAD_REQUEST,
                        "MR_400"
                );
            }
        }

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(request);
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setAppointment(appointment);

        if (medicalRecord.getVisitDate() == null) {
            medicalRecord.setVisitDate(LocalDate.now());
        }

        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toResponse(savedRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordResponse getById(UUID id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException(
                        "Medical record not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        return medicalRecordMapper.toResponse(medicalRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> getByPatient(UUID patientId, int page, int size) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new MedicalRecordException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        return medicalRecordRepository
                .findByPatientOrderByVisitDateDesc(patient, PageRequest.of(page, size))
                .map(medicalRecordMapper::toResponse);
    }

    // ✅ NEW
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> getByPatientEmail(String email, int page, int size) {
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new MedicalRecordException(
                        "Patient not found with email: " + email,
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        return medicalRecordRepository
                .findByPatientOrderByVisitDateDesc(patient, PageRequest.of(page, size))
                .map(medicalRecordMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicalRecordResponse addVitals(UUID id, VitalsRequest request) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException(
                        "Medical record not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        medicalRecord.setWeight(request.getWeight());
        medicalRecord.setHeight(request.getHeight());
        medicalRecord.setTemperature(request.getTemperature());
        medicalRecord.setBloodPressure(request.getBloodPressure());
        medicalRecord.setHeartRate(request.getHeartRate());
        medicalRecord.setSpo2(request.getSpo2());

        MedicalRecord updatedRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toResponse(updatedRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToPdf(UUID id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException(
                        "Medical record not found",
                        HttpStatus.NOT_FOUND,
                        "MR_404"
                ));

        return PdfGenerator.generateMedicalRecordPdf(medicalRecord, medicalRecord.getPatient());
    }
}