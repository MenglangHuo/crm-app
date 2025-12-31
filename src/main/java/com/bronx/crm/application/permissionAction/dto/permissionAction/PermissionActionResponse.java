package com.bronx.crm.application.permissionAction.dto.permissionAction;
import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.application.permission.dto.PermissionResponse;
import com.bronx.crm.application.permissionAction.dto.actions.ActionResponse;


public record PermissionActionResponse(
        Long id,
        PermissionResponse permission,
        ActionResponse action,
        CompanyMainResponse company
) {
}
