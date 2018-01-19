package com.musala.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GatewayNotFoundException extends EntityNotFoundException {
    public GatewayNotFoundException(long id) {
        super("No gateway with id " + id + " exists");
    }
}
