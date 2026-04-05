package com.l2p.hmps.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.l2p.hmps.dto.DepartmentRequest;
import com.l2p.hmps.dto.DepartmentResponse;
import com.l2p.hmps.exception.DoctorException;
import com.l2p.hmps.mapper.DepartmentMapper;
import com.l2p.hmps.model.Department;
import com.l2p.hmps.model.Doctor;
import com.l2p.hmps.repository.DepartmentRepository;
import com.l2p.hmps.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentMapper departmentMapper;

    // ================= CREATE DEPARTMENT =================
    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {

        Department department = departmentMapper.toEntity(request);
        department.setActive(true); // default

        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toResponse(savedDepartment);
    }

    // ================= GET BY ID =================
    @Override
    public DepartmentResponse getById(UUID id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DoctorException(
                        "Department not found with id: " + id,
                        HttpStatus.NOT_FOUND,
                        "DEPT_404"));

        return departmentMapper.toResponse(department);
    }

    // ================= ASSIGN HEAD DOCTOR =================
    @Override
    @Transactional
    public DepartmentResponse assignHeadDoctor(UUID deptId, UUID doctorId) {

        // 1. Find Department
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new DoctorException(
                        "Department not found with id: " + deptId,
                        HttpStatus.NOT_FOUND,
                        "DEPT_404"));

        // 2. Find Doctor
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found with id: " + doctorId,
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"));

        // 3. Validate doctor belongs to department
        if (doctor.getDepartment() == null) {
            throw new DoctorException(
                    "Doctor is not assigned to any department",
                    HttpStatus.BAD_REQUEST,
                    "NO_DEPARTMENT");
        }

        if (!doctor.getDepartment().getId().equals(deptId)) {
            throw new DoctorException(
                    "Doctor does not belong to this department",
                    HttpStatus.BAD_REQUEST,
                    "INVALID_DEPARTMENT");
        }

        // 4. Assign head doctor
        dept.setHeadDoctorId(doctorId);

        Department updatedDepartment = departmentRepository.save(dept);

        return departmentMapper.toResponse(updatedDepartment);
    }
}