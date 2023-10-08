package met.cs673.team1.validation;

import static met.cs673.team1.validation.DateRangeValidator.INVALID_DATE_ORDER_MESSAGE;
import static met.cs673.team1.validation.DateRangeValidator.MISSING_DATE_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;
import org.hibernate.validator.internal.engine.constraintvalidation.CrossParameterConstraintValidatorContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Reference: https://stackoverflow.com/questions/62984669/junit-test-cases-for-javax-custom-validator
 */
@ExtendWith(MockitoExtension.class)
class DateRangeValidatorTest {

    static final String ID = "id";
    static final String START = "start";
    static final String END = "end";
    static final LocalDate DATE_ONE = LocalDate.of(2023, 9, 10);
    static final LocalDate DATE_TWO = LocalDate.of(2023, 9, 10);
    static final LocalDate DATE_THREE = LocalDate.of(2023, 9, 12);

    @Mock
    CrossParameterConstraintValidatorContextImpl context;

    @Mock
    HibernateConstraintViolationBuilder violationBuilder;

    @Mock
    ValidateDateRange annotation;

    DateRangeValidator validator;

    @BeforeEach
    void setUp() {
        doReturn(START).when(annotation).start();
        doReturn(END).when(annotation).end();
        validator = new DateRangeValidator();
        validator.initialize(annotation);
        doReturn(Arrays.asList(ID, START, END)).when(context).getMethodParameterNames();
    }

    @Test
    void testNoDatesValidation() {
        Object[] params = new Object[]{1, null, null};
        assertTrue(validator.isValid(params, context));
    }

    @Test
    void testTwoDatesValidation() {
        Object[] params = new Object[]{1, DATE_ONE, DATE_THREE};
        assertTrue(validator.isValid(params, context));
    }

    @Test
    void testStartEqualsEndDateIsValid() {
        Object[] params = new Object[]{1, DATE_ONE, DATE_TWO};
        assertTrue(validator.isValid(params, context));
    }

    @Test
    void testMissingEndDateValidation() {
        Object[] params = new Object[]{1, DATE_ONE, null};
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();

        assertFalse(validator.isValid(params, context));
        verify(context).buildConstraintViolationWithTemplate(MISSING_DATE_MESSAGE);
    }

    @Test
    void testMissingStartDateValidation() {
        Object[] params = new Object[]{1, null, DATE_THREE};
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();

        assertFalse(validator.isValid(params, context));
        verify(context).buildConstraintViolationWithTemplate(MISSING_DATE_MESSAGE);
    }

    @Test
    void testDatesBackwardsValidation() {
        Object[] params = new Object[]{1, DATE_THREE, DATE_ONE};
        doReturn(violationBuilder).when(context).buildConstraintViolationWithTemplate(anyString());
        doReturn(context).when(violationBuilder).addConstraintViolation();

        assertFalse(validator.isValid(params, context));
        verify(context).buildConstraintViolationWithTemplate(INVALID_DATE_ORDER_MESSAGE);
    }
}
