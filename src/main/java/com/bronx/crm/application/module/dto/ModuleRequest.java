package com.bronx.crm.application.module.dto;

public record ModuleRequest(
        @jakarta.validation.constraints.NotBlank(message = "Module name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @jakarta.validation.constraints.NotNull(message = "Company ID is required")
        Long companyId
) {
}
