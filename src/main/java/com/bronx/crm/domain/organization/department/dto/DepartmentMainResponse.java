package com.bronx.crm.domain.organization.department.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DepartmentMainResponse(
        Long id,
        String name,
        DepartmentMainResponse department
) {
}
