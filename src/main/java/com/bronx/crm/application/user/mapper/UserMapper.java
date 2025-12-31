package com.bronx.crm.application.user.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.application.department.mapper.DepartmentMapper;
import com.bronx.crm.application.division.mapper.DivisionMapper;
import com.bronx.crm.application.user.dto.main.UserMainResponse;
import com.bronx.crm.application.user.dto.main.UserRequest;
import com.bronx.crm.application.user.dto.main.UserResponse;
import com.bronx.crm.domain.user.entity.User;
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
public interface UserMapper {

    UserResponse toResponse(User user);

    UserMainResponse toMainResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uniqueKey", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "division", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    User toEntity(UserRequest request);

    void updateEntityFromRequest(UserRequest request, @MappingTarget User user);
}
