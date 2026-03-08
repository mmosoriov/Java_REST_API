package com.challenge.api.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(UUID uuid) {
        super("Could not find employee with UUID: " + uuid);
    }
}
