package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {


    @Mock
    ExpenseService expenseService;

    @Mock
    UserService userService;

    @InjectMocks
    ExpenseController expenseController;

    @Test
    void testGetAllExpensesById() {
        List<ExpenseDto> expenses = Arrays.asList(new ExpenseDto(), new ExpenseDto());
        doReturn(expenses).when(expenseService).findAllExpensesByUserId(any(Integer.class));
        ResponseEntity<List<ExpenseDto>> response = expenseController.getAllUserExpensesById(1);

        verify(expenseService).findAllExpensesByUserId(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void testAddUserExpenseWhenNoBudgetIsNotPresent() {
        // setup
        String username = "username";
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 25.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);
        dto.setUsername(username);
        dto.setDate(LocalDate.now());

        // setup
        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        UserGetDto fakeDto = UserGetDto.builder().username(username).build();
        doReturn(fakeDto).when(userService).findByUsername(anyString());

        // execution
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto);

        assertNotNull(response.hasBody());
        ExpenseDto result = response.getBody();
        assertFalse(result.getIsOverBudget());

        verify(expenseService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }
    @Test
    void testAddUserExpenseWhenBudgetIsPresent() {
        // setup
        String username = "username";
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 25.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);
        dto.setUsername(username);
        dto.setDate(LocalDate.now());

        // setup
        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        UserGetDto fakeDto = UserGetDto.builder().username(username).budget(100.0).build();
        doReturn(fakeDto).when(userService).findByUsername(anyString());

        // execution
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto);

        assertNotNull(response.hasBody());
        ExpenseDto result = response.getBody();
        assertFalse(result.getIsOverBudget());

        verify(expenseService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }

    @Test
    void testAddUserExpenseWhenSingleExpenseIsOverBudget() {
        // setup
        String username = "username";
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 105.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);
        dto.setUsername(username);
        dto.setDate(LocalDate.now());

        // setup
        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        UserGetDto fakeDto = UserGetDto.builder().username(username).budget(100.0).build();
        doReturn(fakeDto).when(userService).findByUsername(anyString());

        // execution
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto);

        assertNotNull(response.hasBody());
        ExpenseDto result = response.getBody();
        assertTrue(result.getIsOverBudget());

        verify(expenseService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }

    @Test
    void testAddUserExpenseWithMultiplePastExpenses() {
        // setup
        String username = "username";
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 25.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);
        dto.setUsername(username);
        dto.setDate(LocalDate.now());

        // setup
        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        UserGetDto fakeDto = UserGetDto.builder().username(username).budget(100.0).build();
        doReturn(fakeDto).when(userService).findByUsername(anyString());


        // execution

        // add 1st expense (total 60.25)
        ExpenseDto dto1 = new ExpenseDto();
        dto1.setCategory(GROCERIES);
        dto1.setAmount(60.25);
        dto1.setUsername(username);
        dto1.setDate(LocalDate.now());
        expenseController.addUserExpense(dto1);

        // add 2nd expense (total = 80.5)
        ExpenseDto dto2= new ExpenseDto();
        dto2.setCategory(GROCERIES);
        dto2.setAmount(20.25);
        dto2.setUsername(username);
        dto2.setDate(LocalDate.now());
        expenseController.addUserExpense(dto2);

        // finally add the expense to take it over budget (total 105.75)
        List<ExpenseDto> fakeListExpenseDto = new ArrayList<>();
        fakeListExpenseDto.add(dto1);
        fakeListExpenseDto.add(dto2);
        doReturn(fakeListExpenseDto).when(expenseService).findAllExpensesByUsername(anyString());
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto);

        assertNotNull(response.hasBody());
        ExpenseDto result = response.getBody();
        assertTrue(result.getIsOverBudget());

        verify(expenseService).save(dto);
        verify(expenseService).save(dto1);
        verify(expenseService).save(dto2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }
    @Test
    void testAddUserExpenseWithMultiplePastExpensesInADifferentMonth() {
        // setup
        String username = "username";
        String GROCERIES = "Groceries";
        Double GROCERIES_AMOUNT = 25.50;
        ExpenseDto dto = new ExpenseDto();
        dto.setCategory(GROCERIES);
        dto.setAmount(GROCERIES_AMOUNT);
        dto.setUsername(username);
        dto.setDate(LocalDate.now().minusDays(45));

        // setup
        doReturn(dto).when(expenseService).save(any(ExpenseDto.class));
        UserGetDto fakeDto = UserGetDto.builder().username(username).budget(100.0).build();
        doReturn(fakeDto).when(userService).findByUsername(anyString());

        // execution
        expenseController.addUserExpense(dto);

        ExpenseDto dto1 = new ExpenseDto();
        dto1.setCategory(GROCERIES);
        dto1.setAmount(60.25);
        dto1.setUsername(username);
        dto1.setDate(LocalDate.now());
        expenseController.addUserExpense(dto1);

        ExpenseDto dto2= new ExpenseDto();
        dto2.setCategory(GROCERIES);
        dto2.setAmount(20.25);
        dto2.setUsername(username);
        dto2.setDate(LocalDate.now());
        ResponseEntity<ExpenseDto> response = expenseController.addUserExpense(dto2);

        assertNotNull(response.hasBody());
        ExpenseDto result = response.getBody();
        assertFalse(result.getIsOverBudget());

        verify(expenseService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }
}

