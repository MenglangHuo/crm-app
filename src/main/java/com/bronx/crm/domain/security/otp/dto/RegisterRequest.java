package com.bronx.crm.domain.security.otp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Firstname is required")
    @Size(max = 255, message = "Firstname must not exceed 255 characters")
    private String firstname;

    @Size(max = 255, message = "Lastname must not exceed 255 characters")
    private String lastname;

    @Email(message = "Invalid email format")
    private String email;

    // NEW: Phone number field
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone;

    // NEW: Country code
    @Pattern(regexp = "^\\+[1-9]\\d{0,3}$", message = "Invalid country code format (e.g., +1, +855)")
    private String countryCode="+855";

    // NEW: Preferred OTP delivery method
    private String otpDeliveryMethod = "SMS"; // EMAIL, SMS, BOTH

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;

    private Long companyId;
    private Long divisionId;
    private Long departmentId;
}
