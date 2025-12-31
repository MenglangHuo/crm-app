package com.bronx.crm.application.employee.dto.main;

public record EmployeeMainResponse(
         Long id,
         String firstname,
         String lastname,
         String email,
         String username
) {
}
