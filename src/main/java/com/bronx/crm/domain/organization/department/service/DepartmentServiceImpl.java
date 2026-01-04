package com.bronx.crm.domain.organization.department.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.organization.company.entity.Company;
import com.bronx.crm.domain.organization.department.dto.DepartmentRequest;
import com.bronx.crm.domain.organization.department.dto.DepartmentResponse;
import com.bronx.crm.domain.organization.department.mapper.DepartmentMapper;
import com.bronx.crm.domain.organization.department.repository.DepartmentRepository;
import com.bronx.crm.domain.organization.division.repository.DivisionRepository;
import com.bronx.crm.domain.organization.department.entity.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final DivisionRepository divisionRepository;
    private final DepartmentMapper departmentMapper;


    @Override
    public DepartmentResponse create(DepartmentRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));

        if (departmentRepository.existsByCompanyIdAndName( request.companyId(),request.name())) {
            throw new ConflictException("Department", "name",
                    request.name() + " in company " + company.getName());
        }

        Department department = departmentMapper.toEntity(request);
        department.setCompany(company);

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            department.setDivision(division);
        }

        if (request.parentDepartmentId() != null) {
            Department parent = departmentRepository.findById(request.parentDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.parentDepartmentId()));
            department.setParentDepartment(parent);
        }

        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);

    }


    @Override
    public DepartmentResponse get(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        department.setDeletedAt(LocalDateTime.now());
       return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));

        if (departmentRepository.existsByCompanyIdAndName( request.companyId(),request.name(),id)) {
            throw new ConflictException("Department", "name", request.name());
        }

        if (!department.getCompany().getId().equals(request.companyId())) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            department.setCompany(company);
        }

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            department.setDivision(division);
        }

        departmentMapper.updateEntityFromRequest(request, department);
        Department updated = departmentRepository.save(department);
        return departmentMapper.toResponse(updated);
    }

    @Override
    public PageResponse<DepartmentResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Department> pageRes= departmentRepository.findAllByCompanyIdAndName(companyId,query,pageable);
        Page<DepartmentResponse> responsePage = pageRes.map(departmentMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }

    @Override
    public PageResponse<DepartmentResponse> findDepartmentByDivision(PageRequest pageRequest, String name, Long divisionId) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Department> pageRes= departmentRepository.findAllByDivisionId(divisionId,name,pageable);
        Page<DepartmentResponse> responsePage = pageRes.map(departmentMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
