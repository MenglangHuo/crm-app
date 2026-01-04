package com.bronx.crm.domain.identity.user.dto.main;
import com.bronx.crm.common.dto.BaseDto;
import com.bronx.crm.domain.organization.company.dto.CompanyMainResponse;
import com.bronx.crm.domain.organization.department.dto.DepartmentMainResponse;
import com.bronx.crm.domain.organization.division.dto.DivisionMainResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseDto {
    private Long id;
    private UUID uniqueKey;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String attributes;
    private CompanyMainResponse company;
    private DivisionMainResponse division;
    private DepartmentMainResponse department;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastLogin;
    private Short loginAttempt;
    private Boolean phoneVerified;
    private Boolean emailVerified;
    private List<String> roles;
    private List<String> permissions;
}
