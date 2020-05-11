package com.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueLoginValidator.class)
@Documented
public @interface UniqueLogin {

    String message() default "Such login already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
