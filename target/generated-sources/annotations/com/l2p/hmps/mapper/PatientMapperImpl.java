package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.PatientRequest;
import com.l2p.hmps.dto.PatientResponse;
import com.l2p.hmps.model.Patient;
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
public class PatientMapperImpl implements PatientMapper {

    @Override
    public Patient toEntity(PatientRequest request) {
        if ( request == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setAddress( request.getAddress() );
        patient.setBloodGroup( request.getBloodGroup() );
        patient.setDateOfBirth( request.getDateOfBirth() );
        patient.setEmergencyContact( request.getEmergencyContact() );
        patient.setFirstName( request.getFirstName() );
        patient.setGender( request.getGender() );
        patient.setInsuranceInfo( request.getInsuranceInfo() );
        patient.setLastName( request.getLastName() );
        patient.setPhone( request.getPhone() );

        return patient;
    }

    @Override
    public PatientResponse toResponse(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientResponse patientResponse = new PatientResponse();

        patientResponse.setUserId( patientUserId( patient ) );
        patientResponse.setAddress( patient.getAddress() );
        patientResponse.setBloodGroup( patient.getBloodGroup() );
        patientResponse.setDateOfBirth( patient.getDateOfBirth() );
        patientResponse.setEmergencyContact( patient.getEmergencyContact() );
        patientResponse.setFirstName( patient.getFirstName() );
        patientResponse.setGender( patient.getGender() );
        patientResponse.setId( patient.getId() );
        patientResponse.setInsuranceInfo( patient.getInsuranceInfo() );
        patientResponse.setLastName( patient.getLastName() );
        patientResponse.setNhsId( patient.getNhsId() );
        patientResponse.setPhone( patient.getPhone() );

        return patientResponse;
    }

    @Override
    public void updateEntityFromRequest(PatientRequest request, Patient entity) {
        if ( request == null ) {
            return;
        }

        entity.setAddress( request.getAddress() );
        entity.setBloodGroup( request.getBloodGroup() );
        entity.setDateOfBirth( request.getDateOfBirth() );
        entity.setEmergencyContact( request.getEmergencyContact() );
        entity.setFirstName( request.getFirstName() );
        entity.setGender( request.getGender() );
        entity.setInsuranceInfo( request.getInsuranceInfo() );
        entity.setLastName( request.getLastName() );
        entity.setNhsId( request.getNhsId() );
        entity.setPhone( request.getPhone() );
    }

    private UUID patientUserId(Patient patient) {
        if ( patient == null ) {
            return null;
        }
        User user = patient.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
