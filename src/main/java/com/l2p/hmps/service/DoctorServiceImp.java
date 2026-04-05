package com.l2p.hmps.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.l2p.hmps.dto.DoctorDashboardResponse;
import com.l2p.hmps.dto.DoctorRequest;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.model.Department;
import com.l2p.hmps.model.Doctor;
import com.l2p.hmps.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.l2p.hmps.exception.DoctorException;
import com.l2p.hmps.mapper.DoctorMapper;
import com.l2p.hmps.repository.DoctorRepository;
import com.l2p.hmps.repository.DepartmentRepository;
import com.l2p.hmps.repository.UserRepository;

@Service
public class DoctorServiceImp implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new DoctorException(
                "User not found",
                HttpStatus.NOT_FOUND,
                "USER_404"
        ));
    }

    @Override
    public DoctorResponse register(DoctorRequest request) {
        User user = getCurrentUser();

        Doctor doctor = doctorMapper.toEntity(request);
        doctor.setUser(user);

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new DoctorException(
                            "Department not found with id: " + request.getDepartmentId(),
                            HttpStatus.NOT_FOUND,
                            "DEPT_404"));

            doctor.setDepartment(dept);
        }

        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorDashboardResponse getDashboard() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Doctor doctor = doctorRepository.findByUser_Email(email)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"
                ));

        return DoctorDashboardResponse.builder()
                .totalPatientsCount(0L)
                .pendingRecordsCount(0L)
                .averageRating(
                        doctor.getRating() != null ? doctor.getRating() : BigDecimal.ZERO
                )
                .upcomingAppointments(List.of())
                .build();
    }

    @Override
    public DoctorResponse getByEmail(String email) {
        Doctor doctor = doctorRepository.findByUser_Email(email)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"));
        return doctorMapper.toResponse(doctor);
    }

    @Override
    public DoctorResponse getMyProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Doctor doctor = doctorRepository.findByUser_Email(email)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found",
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"
                ));

        return doctorMapper.toResponse(doctor);
    }

    @Override
    public List<DoctorResponse> getDoctorsByDepartment(UUID deptId) {

        List<Doctor> doctors = doctorRepository.findByDepartment_Id(deptId);

        return doctors.stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }
}
