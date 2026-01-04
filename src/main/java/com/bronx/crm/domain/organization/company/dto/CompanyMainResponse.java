package com.bronx.crm.domain.organization.company.dto;

public record CompanyMainResponse(
         Long id,
         String name,
         String note,
         String address
) {
}
