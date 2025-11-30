package com.leaveflow.employeeservice.service;

import com.leaveflow.employeeservice.dto.EmployeeRequest;
import com.leaveflow.employeeservice.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getEmployeesByTeam(String team);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeByEmail(String email);
}