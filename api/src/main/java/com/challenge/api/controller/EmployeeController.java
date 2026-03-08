package com.challenge.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeModelAssembler;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeService employeeService, EmployeeModelAssembler assembler) {
        this.employeeService = employeeService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {
        List<EntityModel<Employee>> employees = employeeService.getAllEmployees().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                employees,
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @GetMapping("/{uuid}")
    public EntityModel<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee = employeeService
                .getEmployeeByUuid(uuid) //
                .orElseThrow(() -> new EmployeeNotFoundException(uuid));
        return assembler.toModel(employee);
    }
    /**
     *TODO LATER
     */
    @PostMapping
    public ResponseEntity<EntityModel<Employee>> createEmployee(@RequestBody Employee newEmployee) {
        Employee created = employeeService.createEmployee(newEmployee);
        EntityModel<Employee> entityModel = assembler.toModel(created);
        return ResponseEntity.created(
                        entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
