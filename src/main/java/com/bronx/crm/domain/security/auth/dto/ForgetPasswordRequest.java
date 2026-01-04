package com.bronx.crm.domain.security.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record ForgetPasswordRequest(
        @NotEmpty(message = "Phone number is required")
         String phone
) {
}
