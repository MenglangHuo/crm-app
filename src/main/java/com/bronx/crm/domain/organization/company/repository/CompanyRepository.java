package com.bronx.crm.domain.organization.company.repository;

import com.bronx.crm.domain.organization.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select (count(c) > 0) from Company c where c.name = ?1 and c.id != ?2")
    boolean existsByNameAndIdNotIn(String name, Long id);

    @Query("select (count(c) > 0) from Company c where c.name = ?1")
    boolean existsByName(String name);


    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name% AND c.deletedAt IS NULL")
    Page<Company> searchByName(@Param("name") String name, Pageable pageable);


    boolean existsByNameAndDeletedAtIsNull(String name);

    @Modifying
    @Query("UPDATE Company c SET c.deletedAt = :deletedAt WHERE c.id = :id")
    void softDelete(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
}