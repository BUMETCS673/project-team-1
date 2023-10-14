package met.cs673.team1.validation;

import static met.cs673.team1.common.StringConstants.MONTHS;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import met.cs673.team1.common.MonthYearFormatter;

public class MonthYearValidator implements ConstraintValidator<ValidMonthYear, String> {

    public static final String GUIDANCE =
            "Parameter should be in the format MMMyyyy (e.g. jun2023), with a valid 3-letter month abbreviation.";
    public static final String INVALID_MESSAGE = "Invalid month/year string.";

    private final MonthYearFormatter formatter;

    public MonthYearValidator(MonthYearFormatter formatter) {
        this.formatter = formatter;
    }

    public boolean isValid(String monthYear, ConstraintValidatorContext context) {
        boolean valid = true;
        Pattern p = Pattern.compile("^([a-zA-Z]{3})([1-9][0-9]{3})$");
        Matcher m = p.matcher(monthYear);

        // Does the input match the above pattern? Is the month a valid 3-letter month abbreviation?
        if (!m.find() || !MONTHS.contains(m.group(1).toLowerCase())) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.join(" ", INVALID_MESSAGE, GUIDANCE))
                    .addConstraintViolation();
        }

        return valid;
    }
}
