package com.bronx.crm.domain.identity.module.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.domain.identity.module.dto.ModuleRequest;
import com.bronx.crm.domain.identity.module.dto.ModuleResponse;
import com.bronx.crm.domain.identity.module.mapper.ModuleMapper;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.identity.action.entity.Action;
import com.bronx.crm.domain.identity.module.repository.ModuleRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.domain.organization.company.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final CompanyRepository companyRepository;
    private final ModuleMapper moduleMapper;

    @Override
    public ModuleResponse create(ModuleRequest request) {
        if (moduleRepository.existsByNameAndCompanyId(request.name(),request.companyId())) {
            throw new ConflictException("Module", "name", request.name());
        }

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));

        Action.Module module = moduleMapper.toEntity(request);
        module.setCompany(company);

        Action.Module saved = moduleRepository.save(module);
        return moduleMapper.toResponse(saved);
    }

    @Override
    public ModuleResponse get(Long id) {
        Action.Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return moduleMapper.toResponse(module);
    }

    @Override
    public ModuleResponse delete(Long id) {
        Action.Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        module.setDeletedAt(LocalDateTime.now());
       return moduleMapper.toResponse(moduleRepository.save(module));
    }

    @Override
    public ModuleResponse update(Long id, ModuleRequest request) {
        Action.Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));

        if (moduleRepository.existsByNameAndCompanyIdAndIdNot(request.name(),request.companyId(),id)) {
            throw new ConflictException("Module", "name", request.name());
        }

        if (!module.getCompany().getId().equals(request.companyId())) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            module.setCompany(company);
        }

        moduleMapper.updateEntityFromRequest(request, module);
        Action.Module updated = moduleRepository.save(module);
        return moduleMapper.toResponse(updated);
    }

    @Override
    public PageResponse<ModuleResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Action.Module> page = moduleRepository.findByCompanyIdAndName(companyId,query, pageable);
        Page<ModuleResponse> responsePage = page.map(moduleMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
