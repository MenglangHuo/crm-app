package com.bronx.crm.domain.organization.division.dto;

public record DivisionRequest(
        @jakarta.validation.constraints.NotNull(message = "Company ID is required")
        Long companyId,
        @jakarta.validation.constraints.NotBlank(message = "Division name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 255 characters")
        String name,
        String note,
        String description) {
}
