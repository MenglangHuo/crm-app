package com.bronx.crm.application.user.service.impl;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.department.repository.DepartmentRepository;
import com.bronx.crm.application.division.repository.DivisionRepository;
import com.bronx.crm.application.user.dto.main.UserRequest;
import com.bronx.crm.application.user.dto.main.UserResponse;
import com.bronx.crm.application.user.mapper.UserMapper;
import com.bronx.crm.application.user.repository.UserRepository;
import com.bronx.crm.application.user.service.UserService;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.user.entity.User;
import com.bronx.crm.domain.organization.entity.Department;
import com.bronx.crm.domain.organization.entity.Division;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DivisionRepository divisionRepository;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("user", "username", request.username());
        }

        if (request.email() != null && userRepository.existsByEmail(request.email())) {
            throw new ConflictException("user", "email", request.email());
        }

        if (request.phone() != null && userRepository.existsByPhone(request.phone())) {
            throw new ConflictException("User", "phone", request.phone());
        }

        User user = userMapper.toEntity(request);
        user.setUniqueKey(UUID.randomUUID());
        user.setLastLogin(LocalDate.now());

        // TODO: Hash password before saving
        // User.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            user.setCompany(company);
        }

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            user.setDivision(division);
        }

        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.departmentId()));
            user.setDepartment(department);
        }

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setDeletedAt(LocalDateTime.now());
       return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.email() != null && !request.email().equals(user.getEmail()) &&
                userRepository.existsByEmail(request.email(),id)) {
            throw new ConflictException("User", "email", request.email());
        }

        if (request.phone() != null && !request.phone().equals(user.getPhone()) &&
                userRepository.existsByPhone(request.phone(),id)) {
            throw new ConflictException("User", "phone", request.email());
        }

        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            user.setCompany(company);
        }

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            user.setDivision(division);
        }

        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.departmentId()));
            user.setDepartment(department);
        }

        userMapper.updateEntityFromRequest(request, user);
        User updated = userRepository.save(user);
        return userMapper.toResponse(updated);

        }

    @Override
    public PageResponse<UserResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<User> page = userRepository.search(companyId, query,pageable);
        Page<UserResponse> responsePage = page.map(userMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }


    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse findByUUID(String uuid) {
        User emp=
                userRepository.findByUniqueKey(uuid).orElseThrow(()->new ResourceNotFoundException("User", "uuid", uuid));

        return userMapper.toResponse(emp);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User emp=
                userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "email", email));

        return userMapper.toResponse(emp);
    }

    @Override
    public UserResponse findByPhone(String phone) {
        User emp=
                userRepository.findByPhone(phone).orElseThrow(()->new ResourceNotFoundException("User", "phone", phone));

        return userMapper.toResponse(emp);
    }
}
