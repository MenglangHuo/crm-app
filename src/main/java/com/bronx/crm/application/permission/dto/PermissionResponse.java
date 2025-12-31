package com.bronx.crm.application.permission.dto;
import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private CompanyMainResponse company;
}
