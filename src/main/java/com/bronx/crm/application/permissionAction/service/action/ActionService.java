package com.bronx.crm.application.permissionAction.service.action;

import com.bronx.crm.application.permissionAction.dto.actions.ActionRequest;
import com.bronx.crm.application.permissionAction.dto.actions.ActionResponse;
import com.bronx.crm.common.dto.BaseService;

import java.util.List;

public interface ActionService extends BaseService<ActionRequest, ActionResponse> {

    List<ActionResponse> crud(List<ActionRequest> actionRequests);
}
