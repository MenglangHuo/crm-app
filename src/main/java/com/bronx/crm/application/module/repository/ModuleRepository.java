package com.bronx.crm.application.module.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Optional<Module> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT m FROM Module m WHERE m.company.id = :companyId")
    Page<Module> findByCompanyId(@Param("companyId") Long companyId, Pageable pageable);

    @Query("SELECT m FROM Module m WHERE m.company.id = :companyId AND m.deletedAt is null")
    Page<Module> findByCompanyIdAndIsActive(@Param("companyId") Long companyId,
                                            Pageable pageable);
}
