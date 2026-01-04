package com.bronx.crm.domain.identity.permission.dto;

public record PermissionRequest(
        @jakarta.validation.constraints.NotBlank(message = "Permission name is required")
        @jakarta.validation.constraints.Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        String description,

        @jakarta.validation.constraints.NotNull(message = "Company ID is required")
        Long companyId
) {
}
