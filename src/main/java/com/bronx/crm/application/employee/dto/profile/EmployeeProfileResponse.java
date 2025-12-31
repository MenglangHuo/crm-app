package com.bronx.crm.application.employee.dto.profile;
import com.bronx.crm.application.employee.dto.main.EmployeeResponse;
import com.bronx.crm.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeProfileResponse extends BaseDto {
    private Long id;
    private EmployeeResponse employee;
    private String image;
    private String bio;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

}
