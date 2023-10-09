package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
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
    void testAddUserExpense() {
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
        assertTrue(result.getIsOverBudget());

        verify(expenseService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getCategory()).isEqualTo(GROCERIES);
        assertThat(response.getBody().getAmount()).isEqualTo(GROCERIES_AMOUNT);
    }
}
