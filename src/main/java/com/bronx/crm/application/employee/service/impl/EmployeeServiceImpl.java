package com.bronx.crm.application.employee.service.impl;

import com.bronx.crm.application.company.repository.CompanyRepository;
import com.bronx.crm.application.department.repository.DepartmentRepository;
import com.bronx.crm.application.division.repository.DivisionRepository;
import com.bronx.crm.application.employee.dto.main.EmployeeRequest;
import com.bronx.crm.application.employee.dto.main.EmployeeResponse;
import com.bronx.crm.application.employee.mapper.EmployeeMapper;
import com.bronx.crm.application.employee.repository.EmployeeRepository;
import com.bronx.crm.application.employee.service.EmployeeService;
import com.bronx.crm.common.dto.PageRequest;
import com.bronx.crm.common.dto.PageResponse;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.utils.page.PageUtil;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.employee.entity.Employee;
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

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final DivisionRepository divisionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        if (employeeRepository.existsByUsername(request.username())) {
            throw new ConflictException("Employee", "username", request.username());
        }

        if (request.email() != null && employeeRepository.existsByEmail(request.email())) {
            throw new ConflictException("Employee", "email", request.email());
        }

        if (request.phone() != null && employeeRepository.existsByPhone(request.phone())) {
            throw new ConflictException("Employee", "phone", request.phone());
        }

        Employee employee = employeeMapper.toEntity(request);
        employee.setUniqueKey(UUID.randomUUID());
        employee.setLastLogin(LocalDate.now());

        // TODO: Hash password before saving
        // employee.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            employee.setCompany(company);
        }

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            employee.setDivision(division);
        }

        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.departmentId()));
            employee.setDepartment(department);
        }

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }

    @Override
    public EmployeeResponse view(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setDeletedAt(LocalDateTime.now());
       return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        if (request.email() != null && !request.email().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(request.email(),id)) {
            throw new ConflictException("Employee", "email", request.email());
        }

        if (request.phone() != null && !request.phone().equals(employee.getPhone()) &&
                employeeRepository.existsByPhone(request.phone(),id)) {
            throw new ConflictException("Employee", "phone", request.email());
        }

        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company", "id", request.companyId()));
            employee.setCompany(company);
        }

        if (request.divisionId() != null) {
            Division division = divisionRepository.findById(request.divisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "id", request.divisionId()));
            employee.setDivision(division);
        }

        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.departmentId()));
            employee.setDepartment(department);
        }

        employeeMapper.updateEntityFromRequest(request, employee);
        Employee updated = employeeRepository.save(employee);
        return employeeMapper.toResponse(updated);

        }

    @Override
    public PageResponse<EmployeeResponse> getAllWithCompany(PageRequest pageRequest, Long companyId, String query) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Employee> page = employeeRepository.search(companyId, query,pageable);
        Page<EmployeeResponse> responsePage = page.map(employeeMapper::toResponse);
        return PageUtil.createPageResponse(responsePage);
    }


    @Override
    public EmployeeResponse findByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "username", username));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse findByUUID(String uuid) {
        Employee emp=
                employeeRepository.findByUniqueKey(uuid).orElseThrow(()->new ResourceNotFoundException("Employee", "uuid", uuid));

        return employeeMapper.toResponse(emp);
    }

    @Override
    public EmployeeResponse findByEmail(String email) {
        Employee emp=
                employeeRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Employee", "email", email));

        return employeeMapper.toResponse(emp);
    }

    @Override
    public EmployeeResponse findByPhone(String phone) {
        Employee emp=
                employeeRepository.findByPhone(phone).orElseThrow(()->new ResourceNotFoundException("Employee", "phone", phone));

        return employeeMapper.toResponse(emp);
    }
}
