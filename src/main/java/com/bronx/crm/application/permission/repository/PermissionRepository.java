package com.bronx.crm.application.permission.repository;
import com.bronx.crm.domain.identity.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select (count(p) > 0) from Permission p where p.name = ?1 and p.company.id = ?2")
    boolean existsByNameAndCompanyId(String name, Long companyId);

    @Query("select (count(p) > 0) from Permission p where p.name = ?1 and p.company.id = ?2 and p.id!=?3")
    boolean existsByNameAndCompanyId(String name, Long companyId,Long id);

    @Query("""
    SELECT r FROM Permission r
    WHERE r.company.id = :companyId
      AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
""")
    Page<Permission> findByCompanyIdAndName(
            @Param("companyId") Long companyId,
            @Param("name") String name,
            Pageable pageable);

    @Query("SELECT p FROM Permission p WHERE p.company.id = :companyId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Permission> searchByCompanyIdAndCode(@Param("companyId") Long companyId,
                                              @Param("keyword") String keyword,
                                              Pageable pageable);
}

