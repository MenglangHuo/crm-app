package com.bronx.crm.domain.organization.company.mapper;

import com.bronx.crm.domain.organization.company.dto.CompanyMainResponse;
import com.bronx.crm.domain.organization.company.dto.CompanyRequest;
import com.bronx.crm.domain.organization.company.dto.CompanyResponse;
import com.bronx.crm.domain.organization.company.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    CompanyResponse toResponse(Company company);

    CompanyMainResponse toMainResponse(Company company);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    Company toEntity(CompanyRequest request);

    void updateEntityFromRequest(CompanyRequest request, @MappingTarget Company company);
}
