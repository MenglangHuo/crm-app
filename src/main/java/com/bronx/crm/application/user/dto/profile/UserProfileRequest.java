package com.bronx.crm.application.user.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UserProfileRequest(
        @jakarta.validation.constraints.NotNull(message = "User ID is required")
        Long userId,
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
