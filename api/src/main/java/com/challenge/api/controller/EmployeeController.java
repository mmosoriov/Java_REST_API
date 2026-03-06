package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    // /**
    //  * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
    //  * @param uuid Employee UUID
    //  * @return Requested Employee if exists
    //  */
    // public Employee getEmployeeByUuid(UUID uuid) {
    //     throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    // }

    // /**
    //  * @implNote Need not be concerned with an actual persistence layer.
    //  * @param requestBody hint!
    //  * @return Newly created Employee
    //  */
    // public Employee createEmployee(Object requestBody) {
    //     throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    // }
}
