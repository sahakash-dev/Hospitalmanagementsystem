package com.l2p.hmps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.l2p.hmps.dto.PatientRequest;
import com.l2p.hmps.dto.PatientResponse;
import com.l2p.hmps.model.Patient;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nhsId", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Patient toEntity(PatientRequest request);

    @Mapping(source = "user.id", target = "userId")
    PatientResponse toResponse(Patient patient);

    // This is great for PUT requests to update an existing patient
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromRequest(PatientRequest request, @org.mapstruct.MappingTarget Patient entity);
}