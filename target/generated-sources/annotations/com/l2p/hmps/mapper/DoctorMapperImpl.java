package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.DoctorRequest;
import com.l2p.hmps.dto.DoctorResponse;
import com.l2p.hmps.model.Department;
import com.l2p.hmps.model.Doctor;
import com.l2p.hmps.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:41+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public Doctor toEntity(DoctorRequest request) {
        if ( request == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setBio( request.getBio() );
        doctor.setFirstName( request.getFirstName() );
        doctor.setLastName( request.getLastName() );
        doctor.setLicenseNo( request.getLicenseNo() );
        doctor.setRating( request.getRating() );
        doctor.setSpecialization( request.getSpecialization() );
        doctor.setYearsExperience( request.getYearsExperience() );

        return doctor;
    }

    @Override
    public DoctorResponse toResponse(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setUserId( doctorUserId( doctor ) );
        doctorResponse.setDepartmentId( doctorDepartmentId( doctor ) );
        doctorResponse.setBio( doctor.getBio() );
        doctorResponse.setFirstName( doctor.getFirstName() );
        doctorResponse.setId( doctor.getId() );
        doctorResponse.setLastName( doctor.getLastName() );
        doctorResponse.setLicenseNo( doctor.getLicenseNo() );
        doctorResponse.setRating( doctor.getRating() );
        doctorResponse.setSpecialization( doctor.getSpecialization() );
        doctorResponse.setYearsExperience( doctor.getYearsExperience() );

        return doctorResponse;
    }

    @Override
    public void updateEntityFromRequest(DoctorRequest request, Doctor doctor) {
        if ( request == null ) {
            return;
        }

        doctor.setBio( request.getBio() );
        doctor.setFirstName( request.getFirstName() );
        doctor.setLastName( request.getLastName() );
        doctor.setLicenseNo( request.getLicenseNo() );
        doctor.setRating( request.getRating() );
        doctor.setSpecialization( request.getSpecialization() );
        doctor.setYearsExperience( request.getYearsExperience() );
    }

    private UUID doctorUserId(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }
        User user = doctor.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID doctorDepartmentId(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }
        Department department = doctor.getDepartment();
        if ( department == null ) {
            return null;
        }
        UUID id = department.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
