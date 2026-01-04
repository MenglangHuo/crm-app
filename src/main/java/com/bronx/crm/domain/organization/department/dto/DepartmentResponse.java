package com.bronx.crm.domain.organization.department.dto;

import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import com.bronx.crm.domain.organization.division.dto.DivisionMainResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private CompanyMainResponse company;
    private DivisionMainResponse division;
    private DepartmentMainResponse department;
}

