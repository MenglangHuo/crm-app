package com.bronx.crm.application.employee.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.application.department.mapper.DepartmentMapper;
import com.bronx.crm.application.division.mapper.DivisionMapper;
import com.bronx.crm.application.employee.dto.main.EmployeeMainResponse;
import com.bronx.crm.application.employee.dto.main.EmployeeRequest;
import com.bronx.crm.application.employee.dto.main.EmployeeResponse;
import com.bronx.crm.domain.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {CompanyMapper.class,
                DivisionMapper.class,
                DepartmentMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeResponse toResponse(Employee employee);

    EmployeeMainResponse toMainResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uniqueKey", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "division", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "employeeRoles", ignore = true)
    Employee toEntity(EmployeeRequest request);

    void updateEntityFromRequest(EmployeeRequest request, @MappingTarget Employee employee);
}
