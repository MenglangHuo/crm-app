package com.bronx.crm.application.company.service;

import com.bronx.crm.application.company.dto.CompanyRequest;
import com.bronx.crm.application.company.dto.CompanyResponse;
import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
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
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;


    @Override
    public CompanyResponse create(CompanyRequest dto) {
        if (companyRepository.existsByName(dto.name())) {
            throw new ConflictException("Company", "name", dto.name());
        }
        Company company = companyMapper.toEntity(dto);
        Company saved = companyRepository.save(company);
        return companyMapper.toResponse(saved);
    }

    @Override
    public CompanyResponse view(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return companyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse delete(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        company.setDeletedAt(LocalDateTime.now());
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequest dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));

        if (companyRepository.existsByNameAndIdNotIn(dto.name(),id)) {
            throw new ConflictException("Company", "name", dto.name());
        }

        companyMapper.updateEntityFromRequest(dto, company);
        Company updated = companyRepository.save(company);
        return companyMapper.toResponse(updated);
    }

    @Override
    public PageResponse<CompanyResponse> getAll(int page, int size, String orderBy, String sortBy, String query) {
        PageRequest pageRequest = PageRequest.builder()
                .page(page)
                .sortDirection(orderBy)
                .size(size)
                .sortBy(sortBy)
                .build();

        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Company> pageRes = companyRepository.searchByName(query,pageable);
        Page<CompanyResponse> responsePage = pageRes.map(companyMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
