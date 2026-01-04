package com.bronx.crm.domain.identity.role.repository;

import com.bronx.crm.domain.identity.role.entity.Role;
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
    @Query("SELECT r FROM Role r WHERE r.name = :name AND r.isActive = true")
    Optional<Role> findActiveByName(@Param("name") String name);
    boolean existsByName(String name);

    @Query("select (count(r) > 0) from Role r where r.name = ?1 and r.company.id = ?2")
    boolean existsByNameAndCompanyId(String name,Long companyId);

    @Query("select (count(r) > 0) from Role r where r.name = ?1 and r.company.id = ?2 and r.id!=?3")
    boolean existsByNameAndCompanyId(String name,Long companyId,Long roleId);


    @Query("""
    SELECT r FROM Role r
    WHERE r.company.id = :companyId
      AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
""")
    Page<Role> findByCompanyIdAndName(
            @Param("companyId") Long companyId,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("SELECT r FROM Role r WHERE r.company.id = :companyId AND r.deletedAt is null")
    Page<Role> findByCompanyIdAndIsActive(@Param("companyId") Long companyId,
                                          Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.company.id = :companyId AND LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Role> searchByCompanyIdAndName(@Param("companyId") Long companyId,
                                        @Param("keyword") String keyword,
                                        Pageable pageable);
}