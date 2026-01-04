package com.bronx.crm.domain.organization.division.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("select (count(d) > 0) from Division d where d.name = ?1 and d.company.id = ?2")
    boolean existsByNameAndCompanyId(String name, Long companyId);


    @Query("select (count(d) > 0) from Division d where d.name = ?1 and d.id !=?2 and d.company.id = ?3")
    boolean existsByNameAndIdNotInAndCompanyId(String name,Long id, Long companyId);

    @Query("""
    SELECT r FROM Division r
    WHERE r.company.id = :companyId
      AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
""")
    Page<Division> findAllByCompanyIdAndName(
            @Param("companyId") Long companyId,
            @Param("name") String name,
            Pageable pageable);


}
