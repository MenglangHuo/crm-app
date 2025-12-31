package com.bronx.crm.application.role.repository;

import com.bronx.crm.domain.rbac.entity.Role;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT r FROM Role r WHERE r.company.id = :companyId")
    Page<Role> findByCompanyId(@Param("companyId") Long companyId, Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.company.id = :companyId AND r.deletedAt is null")
    Page<Role> findByCompanyIdAndIsActive(@Param("companyId") Long companyId,
                                          Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.company.id = :companyId AND LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Role> searchByCompanyIdAndName(@Param("companyId") Long companyId,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);
}