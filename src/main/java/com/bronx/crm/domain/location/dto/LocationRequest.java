package com.bronx.crm.domain.location.dto;

public record LocationRequest(
        @jakarta.validation.constraints.NotBlank(message = "Location name is required")
        @jakarta.validation.constraints.Size(max = 255, message = "Name must not exceed 255 characters")
        String name,
        String description
) {
}
