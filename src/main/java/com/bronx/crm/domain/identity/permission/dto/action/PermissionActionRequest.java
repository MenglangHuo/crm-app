package com.bronx.crm.domain.identity.permission.dto.action;

public record PermissionActionRequest(
        @jakarta.validation.constraints.NotBlank(message = "Action name is required")
         Long actionId,
        @jakarta.validation.constraints.NotBlank(message = "Action name is required")
        Long permissionId,
        @jakarta.validation.constraints.NotBlank(message = "Action name is required")
        Long companyId


) {
}
