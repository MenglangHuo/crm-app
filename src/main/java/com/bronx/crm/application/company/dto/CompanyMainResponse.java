package com.bronx.crm.application.company.dto;

public record CompanyMainResponse(
         Long id,
         String name,
         String note,
         String address
) {
}
