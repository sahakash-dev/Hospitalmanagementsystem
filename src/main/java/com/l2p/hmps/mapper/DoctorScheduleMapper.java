package com.l2p.hmps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.l2p.hmps.dto.ScheduleRequest;
import com.l2p.hmps.model.DoctorSchedule;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorScheduleMapper {

    // --- REQUEST TO ENTITY ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true) // Handled in Service by looking up the doctorId
    DoctorSchedule toEntity(ScheduleRequest request);

    // --- UPDATE EXISTING ENTITY ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    void updateEntityFromRequest(ScheduleRequest request, @MappingTarget DoctorSchedule schedule);

    // Note: No toDTO for SlotResponse is needed here as that is
    // logic-heavy and usually handled in the ServiceImp.
}