package com.l2p.hmps.mapper;

import com.l2p.hmps.dto.DepartmentRequest;
import com.l2p.hmps.dto.DepartmentResponse;
import com.l2p.hmps.model.Department;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-13T12:27:40+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public Department toEntity(DepartmentRequest request) {
        if ( request == null ) {
            return null;
        }

        Department department = new Department();

        department.setDescription( request.getDescription() );
        department.setName( request.getName() );

        return department;
    }

    @Override
    public DepartmentResponse toResponse(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setDescription( department.getDescription() );
        departmentResponse.setHeadDoctorId( department.getHeadDoctorId() );
        departmentResponse.setId( department.getId() );
        departmentResponse.setName( department.getName() );

        return departmentResponse;
    }

    @Override
    public void updateEntityFromRequest(DepartmentRequest request, Department department) {
        if ( request == null ) {
            return;
        }

        department.setDescription( request.getDescription() );
        department.setName( request.getName() );
    }
}
