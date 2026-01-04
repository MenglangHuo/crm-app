package com.bronx.crm.domain.security.otp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    // Can be email or phone
    @NotBlank(message = " phone number is required")
    private String identifier;

    // NEW: Specify delivery method
    private String deliveryMethod = "SMS";
}
