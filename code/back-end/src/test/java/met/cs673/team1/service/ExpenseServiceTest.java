package met.cs673.team1.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.ExpenseMapper;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.ExpenseRepository;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepository;
    @Mock
    ExpenseCategoryRepository categoryRepository;
    @Mock
    UserRepository userRepository;
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
        doReturn(dto).when(expenseMapper).expenseToExpenseDto(any(Expense.class));

        List<ExpenseDto> expenses = expenseService.findAllExpensesByUserId(1);

        verify(expenseRepository).findAllByUserUserId(1);
        verify(expenseMapper, times(2)).expenseToExpenseDto(any(Expense.class));
    }

    @Test
    void testSaveExpenseSuccess() {
        String username = "user123";
        User user = new User();
        user.setUsername(username);
        doReturn(Optional.of(user)).when(userRepository).findByUsername(anyString());

        Expense one = new Expense();
        doReturn(one).when(expenseMapper).expenseDtoToExpense(any(ExpenseDto.class));
        doReturn(one).when(expenseRepository).save(any(Expense.class));

        ExpenseDto inDto = new ExpenseDto();
        inDto.setUsername(username);

        ExpenseDto outDto = expenseService.save(inDto);

        verify(expenseMapper).expenseDtoToExpense(inDto);
        verify(userRepository).findByUsername(username);
        verify(expenseRepository).save(one);
        verify(expenseMapper).expenseToExpenseDto(one);
    }

    @Test
    void testSaveExpenseThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> expenseService.save(new ExpenseDto()));
    }

    @Test
    void testSaveExpenseThrowsUserNotFoundException() {
        ExpenseDto dto = new ExpenseDto();
        dto.setUsername("name");

        User u = new User();
        u.setUsername("name");
        Expense exp = new Expense();
        exp.setUser(u);

        doReturn(exp).when(expenseMapper).expenseDtoToExpense(any(ExpenseDto.class));
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());

        assertThrows(UserNotFoundException.class, () -> expenseService.save(dto));
    }

}
