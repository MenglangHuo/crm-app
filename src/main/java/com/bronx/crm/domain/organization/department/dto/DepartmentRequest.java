package com.bronx.crm.domain.organization.department.dto;

public record DepartmentRequest(
        @jakarta.validation.constraints.NotNull(message = "Company ID is required")
        Long companyId,
        Long divisionId,
        Long parentDepartmentId,
        @jakarta.validation.constraints.NotBlank(message = "Department name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 100 characters")
        String name

) {
}
