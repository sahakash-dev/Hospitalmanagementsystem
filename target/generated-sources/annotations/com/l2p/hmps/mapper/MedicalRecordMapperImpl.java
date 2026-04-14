package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.CreateMedicalRecordRequest;
import com.l2p.hmps.dto.MedicalRecordResponse;
import com.l2p.hmps.model.Appointment;
import com.l2p.hmps.model.MedicalRecord;
import com.l2p.hmps.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:39+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class MedicalRecordMapperImpl implements MedicalRecordMapper {

    @Override
    public MedicalRecord toEntity(CreateMedicalRecordRequest request) {
        if ( request == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setBloodPressure( request.getBloodPressure() );
        medicalRecord.setChiefComplaint( request.getChiefComplaint() );
        medicalRecord.setDiagnosis( request.getDiagnosis() );
        medicalRecord.setFollowUpDate( request.getFollowUpDate() );
        medicalRecord.setHeartRate( request.getHeartRate() );
        medicalRecord.setHeight( request.getHeight() );
        medicalRecord.setIcd10Code( request.getIcd10Code() );
        medicalRecord.setSpo2( request.getSpo2() );
        medicalRecord.setSymptoms( request.getSymptoms() );
        medicalRecord.setTemperature( request.getTemperature() );
        medicalRecord.setTreatmentPlan( request.getTreatmentPlan() );
        medicalRecord.setVisitDate( request.getVisitDate() );
        medicalRecord.setWeight( request.getWeight() );

        return medicalRecord;
    }

    @Override
    public MedicalRecordResponse toResponse(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }

        MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();

        medicalRecordResponse.setPatientId( medicalRecordPatientId( medicalRecord ) );
        medicalRecordResponse.setDoctorId( medicalRecordDoctorId( medicalRecord ) );
        UUID id2 = medicalRecordAppointmentId( medicalRecord );
        if ( id2 != null ) {
            medicalRecordResponse.setAppointmentId( id2 );
        }
        else {
            medicalRecordResponse.setAppointmentId( null );
        }
        medicalRecordResponse.setBloodPressure( medicalRecord.getBloodPressure() );
        medicalRecordResponse.setChiefComplaint( medicalRecord.getChiefComplaint() );
        medicalRecordResponse.setDiagnosis( medicalRecord.getDiagnosis() );
        medicalRecordResponse.setFollowUpDate( medicalRecord.getFollowUpDate() );
        medicalRecordResponse.setHeartRate( medicalRecord.getHeartRate() );
        medicalRecordResponse.setHeight( medicalRecord.getHeight() );
        medicalRecordResponse.setIcd10Code( medicalRecord.getIcd10Code() );
        medicalRecordResponse.setId( medicalRecord.getId() );
        medicalRecordResponse.setSpo2( medicalRecord.getSpo2() );
        medicalRecordResponse.setSymptoms( medicalRecord.getSymptoms() );
        medicalRecordResponse.setTemperature( medicalRecord.getTemperature() );
        medicalRecordResponse.setTreatmentPlan( medicalRecord.getTreatmentPlan() );
        medicalRecordResponse.setVisitDate( medicalRecord.getVisitDate() );
        medicalRecordResponse.setWeight( medicalRecord.getWeight() );

        return medicalRecordResponse;
    }

    private UUID medicalRecordPatientId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        User patient = medicalRecord.getPatient();
        if ( patient == null ) {
            return null;
        }
        UUID id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID medicalRecordDoctorId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        User doctor = medicalRecord.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        UUID id = doctor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID medicalRecordAppointmentId(MedicalRecord medicalRecord) {
        if ( medicalRecord == null ) {
            return null;
        }
        Appointment appointment = medicalRecord.getAppointment();
        if ( appointment == null ) {
            return null;
        }
        UUID id = appointment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
