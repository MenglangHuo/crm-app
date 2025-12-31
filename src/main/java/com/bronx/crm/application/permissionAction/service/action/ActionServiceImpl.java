package com.bronx.crm.application.permissionAction.service.action;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.permissionAction.dto.actions.ActionRequest;
import com.bronx.crm.application.permissionAction.dto.actions.ActionResponse;
import com.bronx.crm.application.permissionAction.mapper.ActionMapper;
import com.bronx.crm.application.permissionAction.repository.ActionRepository;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.identity.entity.Action;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService{
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final CompanyRepository companyRepository;

    @Override
    public ActionResponse create(ActionRequest request) {
        if (actionRepository.existsByNameAndCompanyId(request.name(),request.companyId())) {
            throw new ConflictException("Action", "name", request.name());
        }
        Company company = companyRepository.findById(request.companyId()).orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
        Action action = actionMapper.toEntity(request);
        action.setCompany(company);
        return actionMapper.toResponse(actionRepository.save(action));
    }

    @Override
    public ActionResponse get(Long id) {
        return actionMapper.toResponse(actionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", "id", id)));
    }

    @Override
    public ActionResponse delete(Long id) {
       return null;
    }

    @Override
    public ActionResponse update(Long id, ActionRequest request) {

        Action action=actionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action", "id", id));
        if (actionRepository.existsByNameAndCompanyIdAndIdNot(request.name(),request.companyId(),id)) {
            throw new ConflictException("Action", "name", request.name());
        }

        actionMapper.updateAction(request,action);
        return actionMapper.toResponse(actionRepository.save(action));
    }

    @Override
    public PageResponse<ActionResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Action> pageRes = actionRepository.findAllWithCompanyId(companyId,query, pageable);
        Page<ActionResponse> responsePage = pageRes.map(actionMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }


    @Override
    public List<ActionResponse> crud(List<ActionRequest> actionRequests) {
        List<Action> actionList=new ArrayList<>();
        Long companyId=actionRequests.getFirst().companyId();
        Company company=companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));

        for (ActionRequest actionRequest : actionRequests) {
            Action action=actionMapper.toEntity(actionRequest);
            action.setCompany(company);
            actionList.add(action);
        }

       List<Action> actions= actionRepository.saveAll(actionList);
        return actionMapper.toResponseList(actions);
    }
}
