package com.bronx.crm.application.company.dto;

import com.bronx.crm.common.dto.BaseDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyResponse extends BaseDto {
    private Long id;
    private String name;
    private String note;
    private String address;
    private String attributes;
    private String image;
    private Short locationId;

}
