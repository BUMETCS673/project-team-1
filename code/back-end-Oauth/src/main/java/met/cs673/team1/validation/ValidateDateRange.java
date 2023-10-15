package met.cs673.team1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidateDateRange {

    String message() default "Invalid date parameters";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
    String start();
    String end();
}
