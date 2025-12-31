package com.bronx.crm.application.company.dto;

public record CompanyRequest(
        @jakarta.validation.constraints.NotBlank(message = "Company name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 255 characters")
        String name,
        String note,
        @jakarta.validation.constraints.NotBlank(message = "Address is required")
        String address,
        String attributes,
        String image,
        @jakarta.validation.constraints.NotNull(message = "Location ID is required")
        Short locationId
) {
}
