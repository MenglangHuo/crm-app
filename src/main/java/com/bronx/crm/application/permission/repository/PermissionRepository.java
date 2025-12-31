package com.bronx.crm.application.permission.repository;
import com.bronx.crm.domain.rbac.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT p FROM Permission p WHERE p.company.id = :companyId")
    Page<Permission> findByCompanyId(@Param("companyId") Long companyId, Pageable pageable);

    @Query("SELECT p FROM Permission p WHERE p.company.id = :companyId AND LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Permission> searchByCompanyIdAndCode(@Param("companyId") Long companyId,
                                              @Param("keyword") String keyword,
                                              Pageable pageable);
}

