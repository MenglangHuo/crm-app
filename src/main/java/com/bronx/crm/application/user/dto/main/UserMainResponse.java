package com.bronx.crm.application.user.dto.main;

public record UserMainResponse(
         Long id,
         String firstname,
         String lastname,
         String email,
         String username
) {
}
