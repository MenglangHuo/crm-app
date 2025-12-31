package com.bronx.crm.application.employee.mapper;

import com.bronx.crm.application.employee.dto.profile.EmployeeProfileRequest;
import com.bronx.crm.application.employee.dto.profile.EmployeeProfileResponse;
import com.bronx.crm.domain.employee.entity.EmployeeProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses ={EmployeeMapper.class} ,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeProfileMapper {

    EmployeeProfileResponse toResponse(EmployeeProfile profile);

    EmployeeProfile toEntity(EmployeeProfileRequest request);

    void updateEntityFromRequest(EmployeeProfileRequest request, @MappingTarget EmployeeProfile profile);
}
