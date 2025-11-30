package com.leaveflow.employeeservice.dto;

import com.leaveflow.employeeservice.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String team;
    private Long managerId;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.team = employee.getTeam();
        this.managerId = employee.getManagerId();
    }
}