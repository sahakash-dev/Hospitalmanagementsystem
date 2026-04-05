package com.l2p.hmps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.l2p.hmps.dto.DoctorRequest;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.model.Doctor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {

    // --- REQUEST TO ENTITY ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "department", ignore = true) // Handled in Service via departmentId
    Doctor toEntity(DoctorRequest request);

    // --- ENTITY TO RESPONSE ---
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "department.id", target = "departmentId")
    DoctorResponse toResponse(Doctor doctor);

    // --- PARTIAL UPDATE ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateEntityFromRequest(DoctorRequest request, @MappingTarget Doctor doctor);
}