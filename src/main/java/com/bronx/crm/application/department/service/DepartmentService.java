package com.bronx.crm.application.department.service;

import com.bronx.crm.application.department.dto.DepartmentRequest;
import com.bronx.crm.application.department.dto.DepartmentResponse;
import com.bronx.crm.common.dto.BaseService;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;

public interface DepartmentService extends BaseService<DepartmentRequest, DepartmentResponse> {

   PageResponse<DepartmentResponse> findDepartmentByDivision(PageRequest pageRequest,String name, Long divisionId);
}
