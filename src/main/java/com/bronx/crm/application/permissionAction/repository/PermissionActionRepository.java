package com.bronx.crm.application.permissionAction.repository;

import com.bronx.crm.domain.rbac.entity.PermissionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionActionRepository extends JpaRepository<PermissionAction, Long> {

    Optional<PermissionAction> findByName(String name);

    boolean existsByName(String name);
}
