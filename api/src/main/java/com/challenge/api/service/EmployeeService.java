package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        initializeMockDatabase();
    }

    private void initializeMockDatabase() {
        employees.add(new EmployeeImpl(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                "Alice",
                "Johnson",
                98000,
                30,
                "Senior Engineer",
                "alice.johnson@company.com",
                Instant.parse("2020-01-15T10:00:00Z")));
        employees.add(new EmployeeImpl(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                "Bob",
                "Smith",
                75000,
                28,
                "Sales Manager",
                "bob.smith@company.com",
                Instant.parse("2021-03-20T10:00:00Z")));
        employees.add(new EmployeeImpl(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440003"),
                "Carol",
                "White",
                85000,
                35,
                "HR Director",
                "carol.white@company.com",
                Instant.parse("2019-06-10T10:00:00Z")));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    /**
     * TODO: Exceptions handling in other branches,
     */
    public Employee getEmployeeByUuid(UUID uuid) {
        return employees.stream()
                .filter(emp -> emp.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public Employee createEmployee(Employee newEmployee) {
        if (newEmployee.getUuid() == null) {
            newEmployee.setUuid(UUID.randomUUID());
        }
        employees.add(newEmployee);
        return newEmployee;
    }
}
