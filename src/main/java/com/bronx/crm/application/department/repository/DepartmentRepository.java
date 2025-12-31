package com.bronx.crm.application.department.repository;

import com.bronx.crm.domain.organization.entity.Department;
import com.bronx.crm.domain.organization.entity.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department d where d.company.id = ?1 and (d.name is null or d.name=?2)")
    Page<Department> findAllByCompanyIdAndName(Long companyId, String name, Pageable pageable);


    @Query("select d from Department d where d.division.id = ?1 and (d.name is null or d.name=?2)")
    Page<Department> findAllByDivisionId(Long id, String name, Pageable pageable);

    @Query("select (count(d) > 0) from Department d where d.company.id = ?1 and d.name = ?2")
    boolean existsByCompanyIdAndName(Long companyId, String name);

    @Query("select (count(d) > 0) from Department d where d.company.id = ?1 and d.name = ?2 and d.id=?3")
    boolean existsByCompanyIdAndName(Long companyId, String name, Long id);

}
