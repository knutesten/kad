package no.mesan.controllers.validators;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = UserUniqueValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface UserUnique {
    String message() default "{no.mesan.controllers.validators.UserUnique.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
