package com.bronx.crm.application.permissionAction.mapper;

import com.bronx.crm.application.permission.dto.action.PermissionActionRequest;
import com.bronx.crm.application.permission.dto.action.PermissionActionResponse;
import com.bronx.crm.domain.identity.entity.PermissionAction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionActionMapper {

    PermissionActionResponse toResponse(PermissionAction action);

    PermissionAction toEntity(PermissionActionRequest request);
}
