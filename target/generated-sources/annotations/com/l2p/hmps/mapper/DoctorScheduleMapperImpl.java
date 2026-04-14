package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.ScheduleRequest;
import com.l2p.hmps.model.DayOfWeek;
import com.l2p.hmps.model.DoctorSchedule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:41+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DoctorScheduleMapperImpl implements DoctorScheduleMapper {

    @Override
    public DoctorSchedule toEntity(ScheduleRequest request) {
        if ( request == null ) {
            return null;
        }

        DoctorSchedule doctorSchedule = new DoctorSchedule();

        if ( request.getDayOfWeek() != null ) {
            doctorSchedule.setDayOfWeek( Enum.valueOf( DayOfWeek.class, request.getDayOfWeek() ) );
        }
        doctorSchedule.setEndTime( request.getEndTime() );
        doctorSchedule.setSlotDurationMinutes( request.getSlotDurationMinutes() );
        doctorSchedule.setStartTime( request.getStartTime() );

        return doctorSchedule;
    }

    @Override
    public void updateEntityFromRequest(ScheduleRequest request, DoctorSchedule schedule) {
        if ( request == null ) {
            return;
        }

        if ( request.getDayOfWeek() != null ) {
            schedule.setDayOfWeek( Enum.valueOf( DayOfWeek.class, request.getDayOfWeek() ) );
        }
        else {
            schedule.setDayOfWeek( null );
        }
        schedule.setEndTime( request.getEndTime() );
        schedule.setSlotDurationMinutes( request.getSlotDurationMinutes() );
        schedule.setStartTime( request.getStartTime() );
    }
}
