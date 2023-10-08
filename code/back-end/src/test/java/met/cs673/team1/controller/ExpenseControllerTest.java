package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    static final String USERNAME = "username";
    static final LocalDate DATE = LocalDate.of(2023, 9, 12);

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Mock
    ExpenseService expenseService;

    @Mock
    UserService userService;

    @Mock
    MonthYearFormatter formatter;

    @InjectMocks
    ExpenseController expenseController;

    @Test
    void testGetAllExpensesById() {
        List<ExpenseDto> expenses = Arrays.asList(new ExpenseDto(), new ExpenseDto());
        doReturn(expenses).when(expenseService).findAllByUserId(any(Integer.class));
        ResponseEntity<List<ExpenseDto>> response = expenseController.getAllUserExpenses(1, null, null);

        verify(expenseService).findAllByUserId(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void testGetAllExpensesByIdAndDateRange() {
        List<ExpenseDto> expenses = Arrays.asList(new ExpenseDto(), new ExpenseDto());
        doReturn(expenses).when(expenseService)
                .findAllByUserIdAndDateRange(any(Integer.class), any(LocalDate.class), any(LocalDate.class));
        ResponseEntity<List<ExpenseDto>> response = expenseController.getAllUserExpenses(1, DATE, DATE);

        verify(expenseService).findAllByUserIdAndDateRange(1, DATE, DATE);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void testGetAllExpensesByUsername() {
        ExpenseController spyController = spy(expenseController);
        String username = "username";
        Integer userId = 1;
        User u = new User();
        u.setUserId(userId);
        doReturn(u).when(userService).findUserEntityByUsername(anyString());
        doReturn(ResponseEntity.ok(new ArrayList<>()))
                .when(spyController)
                .getAllUserExpenses(anyInt(), any(LocalDate.class), any(LocalDate.class));

        ResponseEntity<List<ExpenseDto>> response = spyController.getAllUserExpenses(username, DATE, DATE);

        verify(userService).findUserEntityByUsername(username);
        verify(spyController).getAllUserExpenses(userId, DATE, DATE);
        assertTrue(response.hasBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetAllExpensesByUsernameAndMonth() {
        String monthYear = "jun2023";
        LocalDate start = LocalDate.of(2023, 6, 1);
        LocalDate end = LocalDate.of(2023, 6, 30);
        doReturn(YearMonth.of(2023, 6)).when(formatter).formatMonthYearString(anyString());

        ExpenseController spyController = spy(expenseController);
        doReturn(ResponseEntity.ok(new ArrayList<>()))
                .when(spyController)
                .getAllUserExpenses(anyString(), any(LocalDate.class), any(LocalDate.class));

        ResponseEntity<List<ExpenseDto>> response = spyController.getAllUserExpenses(USERNAME, monthYear);

        verify(spyController).getAllUserExpenses(stringCaptor.capture(), dateCaptor.capture(), dateCaptor.capture());
        assertThat(stringCaptor.getValue()).isEqualTo(USERNAME);

        LocalDate startArg = dateCaptor.getAllValues().get(0);
        LocalDate endArg = dateCaptor.getAllValues().get(1);

        assertThat(startArg.compareTo(start)).isZero();
        assertThat(endArg.compareTo(end)).isZero();
    }

    @Test
    void testAddUserExpense() {
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 25.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);

        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto);

        verify(expenseService).save(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }
}
