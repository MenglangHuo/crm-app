package com.bronx.crm.application.employee.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EmployeeProfileRequest(
        @jakarta.validation.constraints.NotNull(message = "Employee ID is required")
        Long employeeId,
        String image,
        @jakarta.validation.constraints.Size(max = 500, message = "Bio must not exceed 500 characters")
        String bio,
        String address,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate
        dob,
        String attributes
) {
}
