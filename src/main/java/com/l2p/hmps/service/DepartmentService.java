package com.l2p.hmps.service;

import java.util.UUID;
import com.l2p.hmps.dto.DepartmentRequest;
import com.l2p.hmps.dto.DepartmentResponse;

/**
 * Service interface for Department management and Administrative logic.
 */
public interface DepartmentService {


    DepartmentResponse create(DepartmentRequest request);

    DepartmentResponse getById(UUID id);

    DepartmentResponse assignHeadDoctor(UUID deptId, UUID doctorId);
}