package com.bronx.crm.domain.identity.module.repository;

import com.bronx.crm.domain.identity.action.entity.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Action.Module, Long> {

    Optional<Action.Module> findByName(String name);

    boolean existsByName(String name);

    @Query("select (count(m) > 0) from Module m where m.name = ?1 and m.company.id = ?2")
    boolean existsByNameAndCompanyId(String name,Long companyId);

    @Query("select (count(m) > 0) from Module m where m.name = ?1 and m.company.id = ?2 and m.id=?3")
    boolean existsByNameAndCompanyIdAndIdNot(String name,Long companyId,Long id);

    @Query("""
    SELECT r FROM Module r
    WHERE r.company.id = :companyId
      AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
""")
    Page<Action.Module> findByCompanyIdAndName(
            @Param("companyId") Long companyId,
            @Param("name") String name,
            Pageable pageable
    );


    @Query("SELECT m FROM Module m WHERE m.company.id = :companyId AND m.deletedAt is null")
    Page<Action.Module> findByCompanyIdAndIsActive(@Param("companyId") Long companyId,
                                                   Pageable pageable);
}
