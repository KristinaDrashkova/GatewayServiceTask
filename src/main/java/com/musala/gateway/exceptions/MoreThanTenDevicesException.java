package com.musala.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "No more than 10 devices allowed")
public class MoreThanTenDevicesException extends Exception {
    public MoreThanTenDevicesException(String message) {
        super(message);
    }
}
