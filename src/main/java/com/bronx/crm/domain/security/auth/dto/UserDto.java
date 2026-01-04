package com.bronx.crm.domain.security.auth.dto;


import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private UUID uniqueKey;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String countryCode;
    private Boolean phoneVerified;
    private Boolean emailVerified;
    private List<String> roles;
    private List<String> permissions;
}
