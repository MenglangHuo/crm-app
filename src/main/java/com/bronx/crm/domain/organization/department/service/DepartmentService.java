package com.bronx.crm.domain.organization.department.service;

import com.bronx.crm.common.dto.BaseService;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.domain.organization.department.dto.DepartmentRequest;
import com.bronx.crm.domain.organization.department.dto.DepartmentResponse;

public interface DepartmentService extends BaseService<DepartmentRequest, DepartmentResponse> {

   PageResponse<DepartmentResponse> findDepartmentByDivision(PageRequest pageRequest, String name, Long divisionId);
}
