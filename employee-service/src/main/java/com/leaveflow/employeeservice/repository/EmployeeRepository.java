package com.leaveflow.employeeservice.repository;

import com.leaveflow.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByTeam(String team);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}