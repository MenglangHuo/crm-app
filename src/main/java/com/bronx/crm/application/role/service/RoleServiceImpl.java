package com.bronx.crm.application.role.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.role.dto.RoleRequest;
import com.bronx.crm.application.role.dto.RoleResponse;
import com.bronx.crm.application.role.mapper.RoleMapper;
import com.bronx.crm.application.role.repository.RoleRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.identity.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements  RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final CompanyRepository companyRepository;

    @Override
    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsByNameAndCompanyId(request.name(),request.companyId())) {
            throw new ConflictException("Role", "name", request.name());
        }

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));

        Role role = roleMapper.toEntity(request);
        role.setCompany(company);

        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    @Override
    public RoleResponse get(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        role.setDeletedAt(LocalDateTime.now());
       return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (roleRepository.existsByNameAndCompanyId(request.name(),request.companyId(),id)) {
            throw new ConflictException("Role", "name", request.name());
        }

        if (!role.getCompany().getId().equals(request.companyId())) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            role.setCompany(company);
        }

        roleMapper.updateEntityFromRequest(request, role);
        Role updated = roleRepository.save(role);
        return roleMapper.toResponse(updated);
    }

    @Override
    public PageResponse<RoleResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Role> pageRes = roleRepository.findByCompanyIdAndName(companyId,query, pageable);
        Page<RoleResponse> responsePage = pageRes.map(roleMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
