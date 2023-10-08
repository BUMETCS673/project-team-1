package met.cs673.team1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MonthYearValidator.class)
public @interface ValidMonthYear {

    String message() default "Invalid month parameter";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
