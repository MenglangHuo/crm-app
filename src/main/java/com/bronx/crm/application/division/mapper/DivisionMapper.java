package com.bronx.crm.application.division.mapper;

import com.bronx.crm.application.company.mapper.CompanyMapper;
import com.bronx.crm.application.division.dto.DivisionMainResponse;
import com.bronx.crm.application.division.dto.DivisionRequest;
import com.bronx.crm.application.division.dto.DivisionResponse;
import com.bronx.crm.domain.organization.entity.Division;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

