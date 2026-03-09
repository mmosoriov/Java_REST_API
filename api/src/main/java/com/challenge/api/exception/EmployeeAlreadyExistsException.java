package com.challenge.api.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(UUID uuid) {
        super("Employee with UUID already exists: " + uuid);
    }
}
