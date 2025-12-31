package com.bronx.crm.application.module.mapper;

import com.bronx.crm.application.module.dto.ModuleRequest;
import com.bronx.crm.application.module.dto.ModuleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",

        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {

    ModuleResponse toResponse(Module module);

    Module toEntity(ModuleRequest request);

    void updateEntityFromRequest(ModuleRequest request, @MappingTarget Module module);
}
