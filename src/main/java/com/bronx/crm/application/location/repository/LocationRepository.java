package com.bronx.crm.application.location.repository;

import com.bronx.crm.domain.location.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where (l.name is null or l.name = ?1) and l.deletedAt is null")
    Page<Location> findAllLocationsByName(String name, Pageable pageable);

    @Query("select (count(l) > 0) from Location l where l.name = ?1 and l.id =?2")
    boolean existsByNameAndIdNotIn(String name,Long id);

    @Query("select (count(l) > 0) from Location l where l.name = ?1")
    boolean existsByName(String name);
}
