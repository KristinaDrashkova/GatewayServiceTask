package com.musala.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EnityNotFoundException extends Exception {
    public EnityNotFoundException(String message) {
        super(message);
    }
}
