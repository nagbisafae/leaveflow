package com.leaveflow.employeeservice.service;

import com.leaveflow.employeeservice.dto.EmployeeRequest;
import com.leaveflow.employeeservice.dto.EmployeeResponse;
import com.leaveflow.employeeservice.exception.ResourceNotFoundException;
import com.leaveflow.employeeservice.model.Employee;
import com.leaveflow.employeeservice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        log.info("Creating new employee with email: {}", request.getEmail());

        if (employeeRepository.existsByEmail(request.getEmail())) {
            log.error("Email already exists: {}", request.getEmail());
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setTeam(request.getTeam());
        employee.setManagerId(request.getManagerId());

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Employee created successfully with ID: {}", savedEmployee.getId());

        return new EmployeeResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        log.info("Employee found: {}", employee.getName());

        return new EmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByTeam(String team) {
        log.info("Fetching employees in team: {}", team);

        List<Employee> employees = employeeRepository.findByTeam(team);

        log.info("Found {} employees in team: {}", employees.size(), team);

        return employees.stream()
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        log.info("Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();

        log.info("Total employees found: {}", employees.size());

        return employees.stream()
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeByEmail(String email) {
        log.info("Fetching employee with email: {}", email);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));

        log.info("Employee found: {}", employee.getName());

        return new EmployeeResponse(employee);
    }
}