package com.l2p.hmps.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.l2p.hmps.model.Department;
import com.l2p.hmps.model.Doctor;
import com.l2p.hmps.model.DoctorSchedule;
import com.l2p.hmps.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.l2p.hmps.dto.DoctorDTO;
import com.l2p.hmps.dto.DoctorScheduleDTO;
import com.l2p.hmps.dto.DepartmentDTO;
import com.l2p.hmps.exception.DoctorException;
import com.l2p.hmps.mapper.DoctorMapper;
import com.l2p.hmps.mapper.DoctorScheduleMapper;
import com.l2p.hmps.mapper.DepartmentMapper;
import com.l2p.hmps.repository.DoctorRepository;
import com.l2p.hmps.repository.DoctorScheduleRepository;
import com.l2p.hmps.repository.DepartmentRepository;
import com.l2p.hmps.repository.UserRepository;

@Service
public class DoctorServiceImp implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorScheduleMapper scheduleMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    // ================= REGISTER =================
    @Override
    public DoctorDTO register(DoctorDTO doctorDTO) {

        Doctor doctor = doctorMapper.toEntity(doctorDTO);

        User user = userRepository.findById(doctorDTO.getUserId())
                .orElseThrow(() -> new DoctorException(
                        "User not found with id: " + doctorDTO.getUserId(),
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"));

        doctor.setUser(user);



        if (doctorDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(doctorDTO.getDepartmentId())
                    .orElseThrow(() -> new DoctorException(
                            "Department not found with id: " + doctorDTO.getDepartmentId(),
                            HttpStatus.NOT_FOUND,
                            "DEPT_404"));

            doctor.setDepartment(dept);
        }

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toDTO(savedDoctor);
    }

    // ================= AVAILABLE SLOTS =================
    @Override
    public List<String> getAvailableSlots(UUID doctorId, LocalDate date) {

        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDoctor_Id(doctorId);

        // Simplified: just return time ranges (you can enhance later)
        return schedules.stream()
                .map(s -> s.getStartTime() + " - " + s.getEndTime())
                .collect(Collectors.toList());
    }

    // ================= SET SCHEDULE =================
    @Override
    public void setSchedule(UUID doctorId, List<DoctorScheduleDTO> schedules) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found with id: " + doctorId,
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"));

        // delete old
        doctorScheduleRepository.deleteByDoctor_Id(doctorId);

        // save new
        List<DoctorSchedule> newSchedules = schedules.stream().map(dto -> {
            DoctorSchedule schedule = scheduleMapper.toEntity(dto);
            schedule.setDoctor(doctor);
            return schedule;
        }).collect(Collectors.toList());

        doctorScheduleRepository.saveAll(newSchedules);
    }

    // ================= DASHBOARD =================
    @Override
    public Object getDashboard(UUID doctorId) {

        // Placeholder (you will implement later)
        return "Dashboard data for doctor: " + doctorId;
    }

    // ================= ASSIGN HEAD DOCTOR =================
    @Override
    public DepartmentDTO assignHeadDoctor(UUID deptId, UUID doctorId) {

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new DoctorException(
                        "Department not found with id: " + deptId,
                        HttpStatus.NOT_FOUND,
                        "DEPT_404"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorException(
                        "Doctor not found with id: " + doctorId,
                        HttpStatus.NOT_FOUND,
                        "DOCTOR_404"));

        if (doctor.getDepartment() == null || !doctor.getDepartment().getId().equals(deptId)) {
            throw new DoctorException(
                    "Doctor does not belong to this department",
                    HttpStatus.BAD_REQUEST,
                    "INVALID_DEPARTMENT");
        }

        dept.setHeadDoctorId(doctorId);
        Department saved = departmentRepository.save(dept);

        return departmentMapper.toDTO(saved);
    }

    // ================= GET DOCTORS BY DEPARTMENT =================
    @Override
    public List<DoctorDTO> getDoctors(UUID deptId) {

        List<Doctor> doctors = doctorRepository.findByDepartment_Id(deptId);

        return doctors.stream()
                .map(doctorMapper::toDTO)
                .collect(Collectors.toList());
    }
}
