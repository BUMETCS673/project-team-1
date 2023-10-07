package met.cs673.team1.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.mapper.ExpenseMapper;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    static final Integer USER_ID = 1;
    static final String USERNAME = "username";
    static final LocalDate DATE = LocalDate.of(2023, 9, 10);

    @Mock
    ExpenseRepository expenseRepository;
    @Mock
    ExpenseCategoryRepository categoryRepository;
    @Mock
    UserService userService;
    @Mock
    ExpenseMapper expenseMapper;

    @InjectMocks
    ExpenseService expenseService;

    @Test
    void testFindAllExpensesById() {
        Expense one = new Expense();
        Expense two = new Expense();
        doReturn(Arrays.asList(one, two))
                .when(expenseRepository).findAllByUserUserId(any(Integer.class));
        ExpenseDto dto = new ExpenseDto();
        doReturn(dto).doReturn(dto).when(expenseMapper).expenseToExpenseDto(any(Expense.class));

        List<ExpenseDto> expenses = expenseService.findAllByUserId(USER_ID);

        verify(expenseRepository).findAllByUserUserId(USER_ID);
        verify(expenseMapper, times(2)).expenseToExpenseDto(any(Expense.class));
    }

    @Test
    void testFindAllExpensesByIdAndDateRange() {
        Expense one = new Expense();
        Expense two = new Expense();
        doReturn(Arrays.asList(one, two))
                .when(expenseRepository)
                .findAllByUserUserIdAndDateBetween(any(Integer.class), any(LocalDate.class), any(LocalDate.class));
        ExpenseDto dto = new ExpenseDto();
        doReturn(dto).doReturn(dto).when(expenseMapper).expenseToExpenseDto(any(Expense.class));

        List<ExpenseDto> expenses = expenseService.findAllByUserIdAndDateRange(USER_ID, DATE, DATE);

        verify(expenseRepository).findAllByUserUserIdAndDateBetween(USER_ID, DATE, DATE);
        verify(expenseMapper, times(2)).expenseToExpenseDto(any(Expense.class));
    }

    @Test
    void testSaveExpenseSuccess() {
        String username = "user123";
        User user = new User();
        user.setUsername(username);
        doReturn(user).when(userService).findUserEntityByUsername(anyString());

        Expense one = new Expense();
        doReturn(one).when(expenseMapper).expenseDtoToExpense(any(ExpenseDto.class));
        doReturn(one).when(expenseRepository).save(any(Expense.class));

        ExpenseDto inDto = new ExpenseDto();
        inDto.setUsername(username);

        ExpenseDto outDto = expenseService.save(inDto);

        verify(expenseMapper).expenseDtoToExpense(inDto);
        verify(userService).findUserEntityByUsername(username);
        verify(expenseRepository).save(one);
        verify(expenseMapper).expenseToExpenseDto(one);
    }

    @Test
    void testSaveExpenseThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> expenseService.save(new ExpenseDto()));
    }
}
