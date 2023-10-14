package met.cs673.team1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.internal.engine.constraintvalidation.CrossParameterConstraintValidatorContextImpl;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class DateRangeValidator implements ConstraintValidator<ValidateDateRange, Object[]> {

    public static final String MISSING_DATE_MESSAGE = "When retrieving by date range, both start and end date must be included.";
    public static final String INVALID_DATE_ORDER_MESSAGE = "Invalid date range. Start date must be before or equal to end date.";

    private String start;
    private String end;

    public void initialize(ValidateDateRange annotation) {
        start = annotation.start();
        end = annotation.end();
    }

    public boolean isValid(Object[] params, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean valid;

        // Which parameters are the start and end dates?
        CrossParameterConstraintValidatorContextImpl crossParamContext =
                (CrossParameterConstraintValidatorContextImpl) context;
        List<String> paramNames = crossParamContext.getMethodParameterNames();
        int startIdx = paramNames.indexOf(start);
        int endIdx = paramNames.indexOf(end);
        LocalDate startParam = (LocalDate) params[startIdx];
        LocalDate endParam = (LocalDate) params[endIdx];

        if (startParam == null && endParam == null) {
            // No date parameters specified is a valid scenario.
            valid = true;
        } else if (startParam == null || endParam == null) {
            // Is only one of the dates specified? Either both should be specified, or neither should be specified.
            context.buildConstraintViolationWithTemplate(MISSING_DATE_MESSAGE)
                    .addConstraintViolation();
            valid = false;
        } else {
            // We have two dates. Do they represent a valid date range?
            LocalDate startDate = (LocalDate) params[startIdx];
            LocalDate endDate = (LocalDate) params[endIdx];
            valid = startDate.isBefore(endDate) || startDate.isEqual(endDate);
            if (!valid) {
                context.buildConstraintViolationWithTemplate(INVALID_DATE_ORDER_MESSAGE)
                        .addConstraintViolation();
            }
        }

        return valid;
    }
}
