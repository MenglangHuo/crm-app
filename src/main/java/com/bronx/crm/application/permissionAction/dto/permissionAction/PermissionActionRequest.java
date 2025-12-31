package com.bronx.crm.application.permissionAction.dto.permissionAction;

public record PermissionActionRequest(
        Long actionId,
        Long permissionId,
        Long companyId
) {
}
