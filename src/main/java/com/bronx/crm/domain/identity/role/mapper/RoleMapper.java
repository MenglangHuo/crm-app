package com.bronx.crm.domain.identity.role.mapper;

import com.bronx.crm.domain.identity.role.dto.RoleRequest;
import com.bronx.crm.domain.identity.role.dto.RoleResponse;
import com.bronx.crm.domain.identity.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleResponse toResponse(Role role);

    Role toEntity(RoleRequest request);

    void updateEntityFromRequest(RoleRequest request, @MappingTarget Role role);
}
