package com.musala.gateway.annotations;

import com.musala.gateway.utils.IpV4Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpV4Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IpV4Constraint {
    String message() default "Invalid ipv4 address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
