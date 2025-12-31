package com.bronx.crm.application.permissionAction.service.permissionAction;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.permission.dto.action.PermissionActionRequest;
import com.bronx.crm.application.permission.dto.action.PermissionActionResponse;
import com.bronx.crm.application.permission.repository.PermissionRepository;
import com.bronx.crm.application.permissionAction.mapper.PermissionActionMapper;
import com.bronx.crm.application.permissionAction.repository.ActionRepository;
import com.bronx.crm.application.permissionAction.repository.PermissionActionRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.identity.entity.Action;
import com.bronx.crm.domain.identity.entity.Permission;
import com.bronx.crm.domain.identity.entity.PermissionAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionActionServiceImpl implements PermissionActionService {

    private final PermissionActionRepository permissionActionRepository;
    private final PermissionActionMapper permissionActionMapper;
    private final CompanyRepository companyRepository;
    private final PermissionRepository permissionRepository;
    private final ActionRepository actionRepository;

    @Override
    public PermissionActionResponse create(PermissionActionRequest request) {
        Action action=actionRepository.findById(request.actionId()).orElseThrow(()->new RuntimeException("Action not found"));
        Permission permission=permissionRepository.findById(request.permissionId()).orElseThrow(()->new RuntimeException("Permission not found"));
        Company company=companyRepository.findById(request.companyId()).orElseThrow(()->new RuntimeException("Company not found"));

        PermissionAction permissionAction=PermissionAction.builder()
                .action(action)
                .permission(permission)
                .company(company)
                .build();

        return permissionActionMapper.toResponse(permissionActionRepository.save(permissionAction));
    }

    @Override
    public PermissionActionResponse get(Long id) {
       return permissionActionMapper.toResponse(permissionActionRepository.findById(id).orElseThrow(()->new RuntimeException("Permission not found")));
    }

    @Override
    public PermissionActionResponse delete(Long id) {
        PermissionAction permissionAction=permissionActionRepository.findById(id).orElseThrow(()->new RuntimeException("Permission not found"));
         permissionActionRepository.findById(id);
         return permissionActionMapper.toResponse(permissionAction);
    }

    @Override
    public PermissionActionResponse update(Long id, PermissionActionRequest dto) {
        return null;
    }

    @Override
    public PageResponse<PermissionActionResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        return PermissionActionService.super.getAllWithCompany(pageRequest, companyId, query);
    }
}
