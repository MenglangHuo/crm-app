package com.bronx.crm.application.permissionAction.mapper;


import com.bronx.crm.application.permissionAction.dto.actions.ActionRequest;
import com.bronx.crm.application.permissionAction.dto.actions.ActionResponse;
import com.bronx.crm.domain.identity.entity.Action;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActionMapper {

    Action toEntity(ActionRequest request);

    ActionResponse toResponse(Action action);

    List<ActionResponse> toResponseList(List<Action> actions);

    void updateAction(ActionRequest request, @MappingTarget Action action);

}
