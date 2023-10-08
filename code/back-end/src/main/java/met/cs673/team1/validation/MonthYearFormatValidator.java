package met.cs673.team1.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonthYearFormatValidator implements ConstraintValidator<ValidMonthYearFormat, String> {

    public static final String INVALID_MESSAGE = "Invalid month/year string. Parameter should be in the format MMMyyyy (e.g. jun2023).";

    public boolean isValid(String monthYear, ConstraintValidatorContext context) {
        Pattern p = Pattern.compile("^([a-zA-Z]{3})([1-9][0-9]{3})$");
        Matcher m = p.matcher(monthYear);
        if (m.find()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(INVALID_MESSAGE).addConstraintViolation();
        return false;
    }
}
