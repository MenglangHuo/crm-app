package com.bronx.crm.application.division.service;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.division.dto.DivisionRequest;
import com.bronx.crm.application.division.dto.DivisionResponse;
import com.bronx.crm.application.division.mapper.DivisionMapper;
import com.bronx.crm.application.division.repository.DivisionRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.organization.entity.Division;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;
    private final CompanyRepository companyRepository;

    @Override
    public DivisionResponse create(DivisionRequest dto) {
        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", dto.companyId()));

        if (divisionRepository.existsByNameAndCompanyId(dto.name(), dto.companyId())) {
            throw new ConflictException("Division", "name",
                    dto.name() + " in company " + company.getName());
        }

        Division division = divisionMapper.toEntity(dto);
        division.setCompany(company);

        Division saved = divisionRepository.save(division);
        return divisionMapper.toResponse(saved);
    }

    @Override
    public DivisionResponse view(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Division", "id", id));
        return divisionMapper.toResponse(division);
    }

    @Override
    public DivisionResponse delete(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Division", "id", id));
        division.setDeletedAt(LocalDateTime.now());
       return divisionMapper.toResponse(divisionRepository.save(division));
    }

    @Override
    public DivisionResponse update(Long id, DivisionRequest dto) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Division", "id", id));

        if (!division.getName().equals(dto.name()) &&
                divisionRepository.existsByNameAndCompanyId(dto.name(), dto.companyId())) {
            throw new ConflictException("Division", "name", dto.name());
        }

        if (!division.getCompany().getId().equals(dto.companyId())) {
            Company company = companyRepository.findById(dto.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", dto.companyId()));
            division.setCompany(company);
        }

        divisionMapper.updateEntityFromRequest(dto, division);
        Division updated = divisionRepository.save(division);
        return divisionMapper.toResponse(updated);
    }

    @Override
    public PageResponse<DivisionResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Division> pageRes = divisionRepository.findAllByCompanyIdAndName(companyId,query,pageable);
        Page<DivisionResponse> responsePage = pageRes.map(divisionMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }
}
