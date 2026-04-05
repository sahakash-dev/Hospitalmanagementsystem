package com.l2p.hmps.service;

import java.util.UUID;

import com.l2p.hmps.dto.PatientRequest;
import com.l2p.hmps.dto.PatientResponse;
import com.l2p.hmps.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.l2p.hmps.exception.PatientException;
import com.l2p.hmps.mapper.PatientMapper;
import com.l2p.hmps.model.Patient;
import com.l2p.hmps.repository.PatientRepository;
import com.l2p.hmps.repository.UserRepository;

@Service
public class PatientServiceImp implements PatientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    // ✅ Get logged-in user
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new PatientException(
                        "User not found",
                        HttpStatus.NOT_FOUND,
                        "USER_404"
                ));
    }

    // ✅ SAFE auto-create patient
    private Patient getOrCreatePatient(User user) {

        return patientRepository.findByUser_Id(user.getId())
                .orElseGet(() -> {

                    Patient patient = new Patient();
                    patient.setUser(user);

                    // 🔥 FIX: Set required fields to avoid validation error
                    String email = user.getEmail();
                    String namePart = email.split("@")[0];

                    patient.setFirstName(namePart);
                    patient.setLastName("User");

                    patient.setNhsId("NHS-" + System.currentTimeMillis());
                    patient.setActive(true);

                    return patientRepository.save(patient);
                });
    }

    // ✅ REGISTER (manual full profile)
    @Override
    public PatientResponse register(PatientRequest request) {

        User user = getCurrentUser();

        if (patientRepository.existsByUser_Id(user.getId())) {
            throw new PatientException(
                    "Patient already exists for this user",
                    HttpStatus.BAD_REQUEST,
                    "PATIENT_EXISTS"
            );
        }

        Patient patient = patientMapper.toEntity(request);

        patient.setUser(user);
        patient.setNhsId("NHS-" + System.currentTimeMillis());
        patient.setActive(true);

        Patient savedPatient = patientRepository.save(patient);

        return patientMapper.toResponse(savedPatient);
    }

    // ✅ GET (AUTO LINKED)
    @Override
    public PatientResponse getByUserId(UUID userId) {

        User currentUser = getCurrentUser();

        Patient patient;

        if (currentUser.getRole().name().equals("PATIENT")) {
            // 🔥 Always use logged-in user
            patient = getOrCreatePatient(currentUser);
        } else {
            // ADMIN / DOCTOR
            patient = patientRepository.findByUser_Id(userId)
                    .orElseThrow(() -> new PatientException(
                            "Patient not found with userId: " + userId,
                            HttpStatus.NOT_FOUND,
                            "PATIENT_404"
                    ));
        }

        return patientMapper.toResponse(patient);
    }

    @Override
    public Page<PatientResponse> getAll(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    public Page<PatientResponse> search(String q, Pageable pageable) {
        return patientRepository.search(q, pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    public PatientResponse update(UUID id, PatientRequest request) {

        User currentUser = getCurrentUser();

        Patient patient;

        if (currentUser.getRole().name().equals("PATIENT")) {
            // 🔥 Ignore ID → update own profile
            patient = getOrCreatePatient(currentUser);
        } else {
            patient = patientRepository.findById(id)
                    .orElseThrow(() -> new PatientException(
                            "Patient not found with id: " + id,
                            HttpStatus.NOT_FOUND,
                            "PATIENT_404"
                    ));
        }

        patientMapper.updateEntityFromRequest(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.toResponse(updatedPatient);
    }

    @Override
    public PatientResponse update(PatientRequest request) {
        User currentUser = getCurrentUser();

        if (!currentUser.getRole().name().equals("PATIENT")) {
            throw new PatientException(
                    "Only patients can update their own profile without specifying an ID",
                    HttpStatus.FORBIDDEN,
                    "PATIENT_UPDATE_ONLY"
            );
        }

        Patient patient = getOrCreatePatient(currentUser);
        patientMapper.updateEntityFromRequest(request, patient);

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(updatedPatient);
    }

    @Override
    public void delete(UUID id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientException(
                        "Patient not found with id: " + id,
                        HttpStatus.NOT_FOUND,
                        "PATIENT_404"
                ));

        patient.setActive(false);
        patientRepository.save(patient);
    }
}
