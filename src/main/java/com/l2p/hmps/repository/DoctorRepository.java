package com.l2p.hmps.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.l2p.hmps.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findByDepartment_Id(UUID departmentId);

}
