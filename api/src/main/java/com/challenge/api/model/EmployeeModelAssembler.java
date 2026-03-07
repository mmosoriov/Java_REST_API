package com.challenge.api.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.challenge.api.controller.EmployeeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(
                employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeByUuid(employee.getUuid()))
                        .withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("All employees"));
    }
}
