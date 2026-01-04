package com.bronx.crm.domain.identity.user.service;

import com.bronx.crm.domain.identity.user.dto.profile.UserProfileRequest;
import com.bronx.crm.domain.identity.user.dto.profile.UserProfileResponse;
import com.bronx.crm.common.dto.BaseService;

public interface UserProfileService extends BaseService<UserProfileRequest, UserProfileResponse> {

    UserProfileResponse findByUserId(Long userId);
}
