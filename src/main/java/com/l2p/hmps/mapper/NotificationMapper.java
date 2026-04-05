package com.l2p.hmps.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.l2p.hmps.dto.NotificationResponse;
import com.l2p.hmps.model.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

//    @Mapping(source = "user.id", target = "user")
    NotificationResponse toDTO(Notification notification);
}