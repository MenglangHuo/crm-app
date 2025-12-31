package com.bronx.crm.application.department.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.application.department.dto.DepartmentMainResponse;
import com.bronx.crm.application.department.dto.DepartmentRequest;
import com.bronx.crm.application.department.dto.DepartmentResponse;
import com.bronx.crm.application.division.mapper.DivisionMapper;
import com.bronx.crm.domain.organization.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {DivisionMapper.class, DepartmentMapper.class, CompanyMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    DepartmentResponse toResponse(Department department);

    DepartmentMainResponse toMainResponse(Department department);


    Department toEntity(DepartmentRequest request);


    void updateEntityFromRequest(DepartmentRequest request, @MappingTarget Department department);
}
