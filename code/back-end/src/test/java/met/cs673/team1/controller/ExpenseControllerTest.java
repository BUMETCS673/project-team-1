package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    static final LocalDate DATE = LocalDate.of(2023, 9, 12);

    @Mock
    ExpenseService expenseService;

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
