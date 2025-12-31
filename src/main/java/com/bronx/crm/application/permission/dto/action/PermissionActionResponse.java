package com.bronx.crm.application.permission.dto.action;

import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.application.permission.dto.PermissionResponse;
import com.bronx.crm.application.permissionAction.dto.actions.ActionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PermissionActionResponse implements Serializable {
    private Long id;
    private ActionResponse action;
    private PermissionResponse permission;
    private CompanyMainResponse company;
}
