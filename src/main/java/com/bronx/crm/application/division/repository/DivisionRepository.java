package com.bronx.crm.application.division.repository;

import com.bronx.crm.domain.organization.entity.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("select (count(d) > 0) from Division d where d.name = ?1 and d.company.id = ?2")
    boolean existsByNameAndCompanyId(String name, Long companyId);


    @Query("select (count(d) > 0) from Division d where d.name = ?1 and d.id !=?2 and d.company.id = ?3")
    boolean existsByNameAndIdNotInAndCompanyId(String name,Long id, Long companyId);

    @Query("select d from Division d where d.company.id = ?1 and (d.name is null or d.name=?2)")
    Page<Division> findAllByCompanyIdAndName(Long companyId,String name, Pageable pageable);


}
