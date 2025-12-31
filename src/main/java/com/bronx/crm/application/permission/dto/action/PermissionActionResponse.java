package com.bronx.crm.application.permission.dto.action;

import com.bronx.crm.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PermissionActionResponse extends BaseDto implements Serializable {
    private Long id;
    private String name;
}
