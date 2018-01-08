package com.musala.gateway.utils;

import com.musala.gateway.annotations.IpV4Constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IpV4Validator implements ConstraintValidator<IpV4Constraint, String> {
    @Override
    public boolean isValid(String ipv4Address, ConstraintValidatorContext context) {
        return ipv4Address != null && ipv4Address.matches(
                "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    }
}
