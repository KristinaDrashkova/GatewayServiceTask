package com.musala.gateway.utils;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtil {
//    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ValidationUtil() {
    }

    public static <T> boolean isValid(T t){
        return t != null; //&& validator.validate(t).size() == 0;
    }
}
