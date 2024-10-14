package com.evact.trustalent.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { RegisterValidation.class })
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegister {
    String message() default "Invalid Register Request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

