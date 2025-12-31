package com.bronx.crm.application.employee.repository;

import com.bronx.crm.domain.employee.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

    @Query("select (count(e) > 0) from EmployeeProfile e where e.employee.id = ?1")
    boolean existsByEmployeeId(Long employeeId);

    @Query("select e from EmployeeProfile e where e.employee.id = ?1")
    Optional<EmployeeProfile> findByEmployeeId(Long employeeId);
}
