package com.musala.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PeripheralDeviceNotFoundException extends EntityNotFoundException {
    public PeripheralDeviceNotFoundException(long id) {
        super("No peripheral device with id " + id + " exists");
    }
}
