package com.bronx.crm.domain.identity.module.mapper;

import com.bronx.crm.domain.identity.module.dto.ModuleRequest;
import com.bronx.crm.domain.identity.module.dto.ModuleResponse;
import com.bronx.crm.domain.identity.action.entity.Action;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",

        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

    ModuleResponse toResponse(Action.Module module);

    Action.Module toEntity(ModuleRequest request);

    void updateEntityFromRequest(ModuleRequest request, @MappingTarget Action.Module module);
}
