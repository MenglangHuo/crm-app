package com.bronx.crm.application.user.mapper;

import com.bronx.crm.application.user.dto.profile.UserProfileRequest;
import com.bronx.crm.application.user.dto.profile.UserProfileResponse;
import com.bronx.crm.domain.user.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses ={UserMapper.class} ,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProfileMapper {

    UserProfileResponse toResponse(UserProfile profile);

    UserProfile toEntity(UserProfileRequest request);

    void updateEntityFromRequest(UserProfileRequest request, @MappingTarget UserProfile profile);
}
