package com.bronx.crm.application.role.dto;

public record RoleRequest(
        @jakarta.validation.constraints.NotBlank(message = "Role name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 255 characters")
        String name,
        String note,
        @jakarta.validation.constraints.NotNull(message = "Company ID is required")
        Long companyId
        ) {
}
