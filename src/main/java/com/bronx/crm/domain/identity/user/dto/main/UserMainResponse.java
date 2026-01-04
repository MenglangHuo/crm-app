package com.bronx.crm.domain.identity.user.dto.main;

public record UserMainResponse(
         Long id,
         String firstname,
         String lastname,
         String email,
         String username
) {
}
