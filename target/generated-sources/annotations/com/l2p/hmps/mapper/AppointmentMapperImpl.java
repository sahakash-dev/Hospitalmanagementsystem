package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.AppointmentResponse;
import com.l2p.hmps.dto.BookAppointmentRequest;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.AppointmentType;
import com.l2p.hmps.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:40+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public Appointment toEntity(BookAppointmentRequest request) {
        if ( request == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate( request.getAppointmentDate() );
        appointment.setReason( request.getReason() );
        appointment.setSlot( request.getSlot() );
        if ( request.getType() != null ) {
            appointment.setType( Enum.valueOf( AppointmentType.class, request.getType() ) );
        }

        return appointment;
    }

    @Override
    public AppointmentResponse toResponse(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentResponse.AppointmentResponseBuilder appointmentResponse = AppointmentResponse.builder();

        appointmentResponse.patientId( appointmentPatientId( appointment ) );
        appointmentResponse.doctorId( appointmentDoctorId( appointment ) );
        appointmentResponse.appointmentDate( appointment.getAppointmentDate() );
        appointmentResponse.createdAt( appointment.getCreatedAt() );
        appointmentResponse.reason( appointment.getReason() );
        appointmentResponse.slot( appointment.getSlot() );
        if ( appointment.getStatus() != null ) {
            appointmentResponse.status( appointment.getStatus().name() );
        }
        if ( appointment.getType() != null ) {
            appointmentResponse.type( appointment.getType().name() );
        }
        appointmentResponse.updatedAt( appointment.getUpdatedAt() );

        return appointmentResponse.build();
    }

    private UUID appointmentPatientId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        User patient = appointment.getPatient();
        if ( patient == null ) {
            return null;
        }
        UUID id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID appointmentDoctorId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        User doctor = appointment.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        UUID id = doctor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
