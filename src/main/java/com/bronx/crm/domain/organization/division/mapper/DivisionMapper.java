package com.bronx.crm.domain.organization.division.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.domain.organization.division.dto.DivisionMainResponse;
import com.bronx.crm.domain.organization.division.dto.DivisionRequest;
import com.bronx.crm.domain.organization.division.dto.DivisionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {CompanyMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DivisionMapper {


    DivisionResponse toResponse(Division division);

    DivisionMainResponse toMainResponse(Division division);


    Division toEntity(DivisionRequest request);

    void updateEntityFromRequest(DivisionRequest request, @MappingTarget Division division);
}

