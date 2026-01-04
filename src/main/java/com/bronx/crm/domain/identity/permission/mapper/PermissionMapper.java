package com.bronx.crm.domain.identity.permission.mapper;


import com.bronx.crm.domain.identity.permission.dto.PermissionRequest;
import com.bronx.crm.domain.identity.permission.dto.PermissionResponse;
import com.bronx.crm.domain.identity.permission.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    PermissionResponse toResponse(Permission permission);

    Permission toEntity(PermissionRequest request);

    void updateEntityFromRequest(PermissionRequest request, @MappingTarget Permission permission);
}
