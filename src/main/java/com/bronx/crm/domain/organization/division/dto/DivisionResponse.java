package com.bronx.crm.domain.organization.division.dto;

import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DivisionResponse extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private CompanyMainResponse company;

}
