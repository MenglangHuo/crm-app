package com.bronx.crm.domain.identity.user.mapper;
import com.bronx.crm.domain.identity.user.dto.main.UserMainResponse;
import com.bronx.crm.domain.identity.user.dto.main.UserRequest;
import com.bronx.crm.domain.identity.user.dto.main.UserResponse;
import com.bronx.crm.domain.identity.user.entity.User;
import com.bronx.crm.domain.organization.company.mapper.CompanyMapper;
import com.bronx.crm.domain.organization.department.mapper.DepartmentMapper;
import com.bronx.crm.domain.organization.division.mapper.DivisionMapper;
import com.bronx.crm.domain.security.auth.dto.RegisterRequest;
import com.bronx.crm.domain.security.auth.dto.UserDto;
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
    User toEntity(RegisterRequest request);

    UserDto toDto(User user);

    void updateEntityFromRequest(UserRequest request, @MappingTarget User user);
}
