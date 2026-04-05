package com.l2p.hmps.service;

import java.util.List;
import java.util.UUID;
import com.l2p.hmps.dto.DoctorRequest;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.dto.DoctorDashboardResponse;

public interface DoctorService {

    // Register doctor
    DoctorResponse register(DoctorRequest request);

    // Dashboard data
    DoctorDashboardResponse getDashboard();

    DoctorResponse getMyProfile();

    DoctorResponse getByEmail(String email);

    // Get all doctors in a department
    List<DoctorResponse> getDoctorsByDepartment(UUID deptId);
}
