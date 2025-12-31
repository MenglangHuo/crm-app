package com.bronx.crm.application.employee.service;

import com.bronx.crm.application.employee.dto.profile.EmployeeProfileRequest;
import com.bronx.crm.application.employee.dto.profile.EmployeeProfileResponse;
import com.bronx.crm.common.dto.BaseService;

public interface EmployeeProfileService extends BaseService<EmployeeProfileRequest, EmployeeProfileResponse> {

    EmployeeProfileResponse getByEmployeeId(Long employeeId);
}
