package com.l2p.hmps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.l2p.hmps.dto.DepartmentRequest;
import com.l2p.hmps.dto.DepartmentResponse;
import com.l2p.hmps.model.Department;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "headDoctorId", ignore = true) // Handled in Service by ID lookup
    Department toEntity(DepartmentRequest request);

    DepartmentResponse toResponse(Department department);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "headDoctorId", ignore = true)
    void updateEntityFromRequest(DepartmentRequest request, @MappingTarget Department department);
}