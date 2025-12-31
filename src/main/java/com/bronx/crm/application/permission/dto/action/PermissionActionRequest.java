package com.bronx.crm.application.permission.dto.action;

public record PermissionActionRequest(
        @jakarta.validation.constraints.NotBlank(message = "Action name is required")
        @jakarta.validation.constraints.Size(max = 100, message = "Name must not exceed 100 characters")
         String name
) {
}
