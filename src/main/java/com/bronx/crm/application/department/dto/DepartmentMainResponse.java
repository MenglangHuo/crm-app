package com.bronx.crm.application.department.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DepartmentMainResponse(
        Long id,
        String name,
        DepartmentMainResponse department
) {
}
