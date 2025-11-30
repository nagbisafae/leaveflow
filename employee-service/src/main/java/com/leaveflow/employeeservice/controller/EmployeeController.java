package com.leaveflow.employeeservice.controller;

import com.leaveflow.employeeservice.dto.EmployeeRequest;
import com.leaveflow.employeeservice.dto.EmployeeResponse;
import com.leaveflow.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        log.info("POST /employees - Creating employee with email: {}", request.getEmail());

        EmployeeResponse response = employeeService.createEmployee(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

        log.info("GET /employees/{} - Fetching employee", id);

        EmployeeResponse response = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(
            @RequestParam(required = false) String team) {

        if (team != null && !team.isEmpty()) {
            log.info("GET /employees?team={} - Fetching employees by team", team);
            List<EmployeeResponse> responses = employeeService.getEmployeesByTeam(team);
            return ResponseEntity.ok(responses);
        }

        log.info("GET /employees - Fetching all employees");
        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmail(@PathVariable String email) {

        log.info("GET /employees/email/{} - Fetching employee by email", email);

        EmployeeResponse response = employeeService.getEmployeeByEmail(email);

        return ResponseEntity.ok(response);
    }
}