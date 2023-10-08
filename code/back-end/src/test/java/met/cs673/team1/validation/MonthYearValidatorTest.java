package met.cs673.team1.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import jakarta.validation.ConstraintValidatorContext;
import met.cs673.team1.common.MonthYearFormatter;
import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MonthYearValidatorTest {

    @Mock
    HibernateConstraintViolationBuilder violationBuilder;

    @Mock
    ConstraintValidatorContext context;

    @Mock
    MonthYearFormatter formatter;

    MonthYearValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MonthYearValidator(formatter);
    }

    @Test
    void testMonthYearIsValid() {
        String my = "jun2023";
        assertTrue(validator.isValid(my, context));
    }

    @Test
    void testMixedCaseMonthYearIsValid() {
        String my = "jUn2023";
        assertTrue(validator.isValid(my, context));
    }

    @Test
    void testInvalidMonthAbbreviation() {
        String my = "abc2023";
        doNothing().when(context).disableDefaultConstraintViolation();
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();

        validator.isValid(my, context);

        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(
                String.join(" ", MonthYearValidator.INVALID_MESSAGE, MonthYearValidator.GUIDANCE));
        verify(violationBuilder).addConstraintViolation();
    }

    @Test
    void testMonthMissingLetter() {
        String my = "ju2023";
        doNothing().when(context).disableDefaultConstraintViolation();
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();
        assertFalse(validator.isValid(my, context));
    }

    @Test
    void testMonthTooManyLetters() {
        String my = "janu2023";
        doNothing().when(context).disableDefaultConstraintViolation();
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();
        assertFalse(validator.isValid(my, context));
    }

    @Test
    void testYearMissingDigit() {
        String my = "jun202";
        doNothing().when(context).disableDefaultConstraintViolation();
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();
        assertFalse(validator.isValid(my, context));
    }

    @Test
    void testYearTooManyDigits() {
        String my = "jun20233";
        doNothing().when(context).disableDefaultConstraintViolation();
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();
        assertFalse(validator.isValid(my, context));
    }
}
