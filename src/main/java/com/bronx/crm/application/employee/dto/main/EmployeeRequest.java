package com.bronx.crm.application.employee.dto.main;

public record EmployeeRequest(
        @jakarta.validation.constraints.NotBlank(message = "Firstname is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Firstname must not exceed 255 characters")
        String firstname,
        @jakarta.validation.constraints.Size(max = 255, message = "Lastname must not exceed 255 characters")
        String lastname,

        @jakarta.validation.constraints.Email(message = "Invalid email format")
        String email,

        @jakarta.validation.constraints.NotEmpty(message = "Phone Must not be empty")
        @jakarta.validation.constraints.Size(max = 15, message = "Phone number incorrect!",min = 9)
        String phone,

        Long companyId,

        Long divisionId,

        Long departmentId,

        @jakarta.validation.constraints.NotBlank(message = "Username is required")
        @jakarta.validation.constraints.Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @jakarta.validation.constraints.NotBlank(message = "Password is required")
        @jakarta.validation.constraints.Size(min = 6, message = "Password must be at least 6 characters")
        String password
        ) {
}
