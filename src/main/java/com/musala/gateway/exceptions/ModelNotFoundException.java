package com.musala.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ModelNotFoundException extends Exception {
    public ModelNotFoundException(long id, String entityType) {
        super("No " + entityType + " with id: " + id + " exists");
    }
}
