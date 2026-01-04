package com.bronx.crm.domain.identity.role.dto;

import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private CompanyMainResponse company;
}