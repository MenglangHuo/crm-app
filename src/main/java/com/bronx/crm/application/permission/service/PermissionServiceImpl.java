package com.bronx.crm.application.permission.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.permission.dto.PermissionRequest;
import com.bronx.crm.application.permission.dto.PermissionResponse;
import com.bronx.crm.application.permission.mapper.PermissionMapper;
import com.bronx.crm.application.permission.repository.PermissionRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.identity.entity.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsByNameAndCompanyId(request.name(), request.companyId())) {
            throw new ConflictException("Permission", "code", request.name());
        }

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));

        Permission permission = permissionMapper.toEntity(request);
        permission.setCompany(company);

        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toResponse(saved);
    }

    @Override
    public PermissionResponse get(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        return permissionMapper.toResponse(permission);
    }

    @Override
    public PermissionResponse delete(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        permission.setDeletedAt(LocalDateTime.now());
        return permissionMapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        if (permissionRepository.existsByNameAndCompanyId(request.name(), request.companyId(), id)) {
            throw new ConflictException("Permission", "code", request.name());
        }

        if (!permission.getCompany().getId().equals(request.companyId())) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            permission.setCompany(company);
        }

        permissionMapper.updateEntityFromRequest(request, permission);
        Permission updated = permissionRepository.save(permission);
        return permissionMapper.toResponse(updated);
    }

    @Override
    public PageResponse<PermissionResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Permission> page = permissionRepository.findByCompanyIdAndName(companyId, query, pageable);
        Page<PermissionResponse> responsePage = page.map(permissionMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
