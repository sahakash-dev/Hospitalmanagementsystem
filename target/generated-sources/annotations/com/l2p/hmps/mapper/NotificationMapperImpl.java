package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.NotificationResponse;
import com.l2p.hmps.model.Notification;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:40+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponse toDTO(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationResponse.NotificationResponseBuilder notificationResponse = NotificationResponse.builder();

        notificationResponse.id( notification.getId() );
        notificationResponse.message( notification.getMessage() );
        notificationResponse.sentAt( notification.getSentAt() );
        notificationResponse.title( notification.getTitle() );
        notificationResponse.type( notification.getType() );

        return notificationResponse.build();
    }
}
