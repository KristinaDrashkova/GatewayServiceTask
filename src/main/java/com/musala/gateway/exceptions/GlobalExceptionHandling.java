package com.musala.gateway.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(MoreThanTenDevicesException.class)
    public ResponseEntity<String> handleMoreThanTenDevicesException(MoreThanTenDevicesException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getConstraintViolations().iterator().next().getMessageTemplate(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> handleModelNotFoundException(ModelNotFoundException ex) {
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
