package com.bronx.crm.domain.organization.department.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.domain.organization.department.dto.DepartmentMainResponse;
import com.bronx.crm.domain.organization.department.dto.DepartmentRequest;
import com.bronx.crm.domain.organization.department.dto.DepartmentResponse;
import com.bronx.crm.domain.organization.division.mapper.DivisionMapper;
import com.bronx.crm.domain.organization.department.entity.Department;
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
