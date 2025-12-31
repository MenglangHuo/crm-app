package com.bronx.crm.application.module.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.module.dto.ModuleRequest;
import com.bronx.crm.application.module.dto.ModuleResponse;
import com.bronx.crm.application.module.mapper.ModuleMapper;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.identity.entity.Module;
import com.bronx.crm.application.module.repository.ModuleRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.domain.company.entity.Company;
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

        Module module = moduleMapper.toEntity(request);
        module.setCompany(company);

        Module saved = moduleRepository.save(module);
        return moduleMapper.toResponse(saved);
    }

    @Override
    public ModuleResponse get(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return moduleMapper.toResponse(module);
    }

    @Override
    public ModuleResponse delete(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        module.setDeletedAt(LocalDateTime.now());
       return moduleMapper.toResponse(moduleRepository.save(module));
    }

    @Override
    public ModuleResponse update(Long id, ModuleRequest request) {
        Module module = moduleRepository.findById(id)
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
        Module updated = moduleRepository.save(module);
        return moduleMapper.toResponse(updated);
    }

    @Override
    public PageResponse<ModuleResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Module> page = moduleRepository.findByCompanyIdAndName(companyId,query, pageable);
        Page<ModuleResponse> responsePage = page.map(moduleMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
