package com.bronx.crm.application.employee.dto.main;
import com.bronx.crm.application.company.dto.CompanyMainResponse;
import com.bronx.crm.application.department.dto.DepartmentMainResponse;
import com.bronx.crm.application.division.dto.DivisionMainResponse;
import com.bronx.crm.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse extends BaseDto {
    private Long id;
    private UUID uniqueKey;
    private String firstname;
    private String lastname;
    private String email;
    private String attributes;
    private CompanyMainResponse company;
    private DivisionMainResponse divisionId;
    private DepartmentMainResponse department;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastLogin;
    private Short loginAttempt;
}
