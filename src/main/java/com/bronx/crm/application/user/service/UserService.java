package com.bronx.crm.application.user.service;

import com.bronx.crm.application.user.dto.main.UserRequest;
import com.bronx.crm.application.user.dto.main.UserResponse;
import com.bronx.crm.common.dto.BaseService;

public interface UserService extends BaseService<UserRequest, UserResponse> {
    UserResponse findByUsername(String username);
    UserResponse findByUUID(String uuid);
    UserResponse findByEmail(String email);
    UserResponse findByPhone(String phone);
}
