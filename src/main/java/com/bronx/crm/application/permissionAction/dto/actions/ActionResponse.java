package com.bronx.crm.application.permissionAction.dto.actions;

import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import lombok.*;

public record ActionResponse(
        Long id,
        String name,
        CompanyMainResponse company
) {
}
