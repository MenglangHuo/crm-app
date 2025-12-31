package com.bronx.crm.application.employee.service;

import com.bronx.crm.application.employee.dto.main.EmployeeRequest;
import com.bronx.crm.application.employee.dto.main.EmployeeResponse;
import com.bronx.crm.common.dto.BaseService;

import java.util.Optional;

public interface EmployeeService extends BaseService<EmployeeRequest, EmployeeResponse> {
    EmployeeResponse findByUsername(String username);
    EmployeeResponse findByUUID(String uuid);
    EmployeeResponse findByEmail(String email);
    EmployeeResponse findByPhone(String phone);
}
