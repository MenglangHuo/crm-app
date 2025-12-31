package com.bronx.crm.application.permissionAction.dto.actions;

public record ActionRequest(
        String name,
        Long companyId
) {
}
