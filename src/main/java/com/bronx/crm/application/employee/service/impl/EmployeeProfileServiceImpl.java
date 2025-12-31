package com.bronx.crm.application.employee.service.impl;

import com.bronx.crm.application.employee.dto.profile.EmployeeProfileRequest;
import com.bronx.crm.application.employee.dto.profile.EmployeeProfileResponse;
import com.bronx.crm.application.employee.mapper.EmployeeProfileMapper;
import com.bronx.crm.application.employee.repository.EmployeeProfileRepository;
import com.bronx.crm.application.employee.repository.EmployeeRepository;
import com.bronx.crm.application.employee.service.EmployeeProfileService;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.domain.employee.entity.Employee;
import com.bronx.crm.domain.employee.entity.EmployeeProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository profileRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProfileMapper profileMapper;


    @Override
    public EmployeeProfileResponse create(EmployeeProfileRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", request.employeeId()));

        if (profileRepository.existsByEmployeeId(request.employeeId())) {
            throw new ConflictException("EmployeeProfile", "employeeId", request.employeeId());
        }

        EmployeeProfile profile = profileMapper.toEntity(request);
        profile.setEmployee(employee);

        EmployeeProfile saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved);
    }

    @Override
    public EmployeeProfileResponse view(Long id) {
        EmployeeProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeProfile", "id", id));
        return profileMapper.toResponse(profile);
    }

    @Override
    public EmployeeProfileResponse delete(Long id) {
        EmployeeProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeProfile", "id", id));
        profile.setDeletedAt(LocalDateTime.now());
       return profileMapper.toResponse(profileRepository.save(profile));
    }

    @Override
    public EmployeeProfileResponse update(Long id, EmployeeProfileRequest request) {
        EmployeeProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeProfile", "id", id));


        profileMapper.updateEntityFromRequest(request, profile);
        EmployeeProfile updated = profileRepository.save(profile);
        return profileMapper.toResponse(updated);
    }


    @Override
    public EmployeeProfileResponse getByEmployeeId(Long employeeId) {
        EmployeeProfile profile = profileRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeProfile", "employeeId", employeeId));
        return profileMapper.toResponse(profile);
    }
}
