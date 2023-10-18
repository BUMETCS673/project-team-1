package met.cs673.team1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.ExpenseMapper;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.ExpenseRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class providing processing for expense data before persistence.
 */
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(final ExpenseRepository expenseRepository,
                          final ExpenseCategoryRepository expenseCategoryRepository,
                          final UserService userService,
                          final UserRepository userRepository,
                          final ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }

    /**
     * Get all expenses by userId
     * @param userId user id
     * @return List of expenses
     */
    public List<ExpenseDto> findAllByUserId(Integer userId) {
        List<Expense> expenses = expenseRepository.findAllByUserUserId(userId);
        return expenses.stream().map(expenseMapper::expenseToExpenseDto).collect(Collectors.toList());
    }

    /**
     * Find all expenses by username
     * @param username username for search
     * @return List of expenses
     */
    public List<ExpenseDto> findAllExpensesByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username '%s' not found", username));
        }
        List<Expense> expenses = expenseRepository.findAllByUserUserId(optUser.get().getUserId());
        return expenses.stream().map(expenseMapper::expenseToExpenseDto).collect(Collectors.toList());
    }

    /**
     * Get all expenses by userId and date range
     * @param userId user id
     * @param start start date of range, inclusive
     * @param end end date of range, inclusive
     * @return List of expenses
     */
    public List<ExpenseDto> findAllByUserIdAndDateRange(Integer userId, LocalDate start, LocalDate end) {
        List<Expense> expenses = expenseRepository.findAllByUserUserIdAndDateBetween(userId, start, end);
        return expenses.stream().map(expenseMapper::expenseToExpenseDto).collect(Collectors.toList());
    }

    /**
     * Save an expense to the database
     * @param expenseDto Data transfer object representing an expense
     * @return ExpenseDto object with included expense_id on success, or error response from
     * ExceptionHandlerControllerAdvice
     */
    public ExpenseDto save(ExpenseDto expenseDto) {
        if (expenseDto.getUsername() == null) {
            throw new IllegalArgumentException("Expense username cannot be null");
        }
        Expense exp = expenseMapper.expenseDtoToExpense(expenseDto);
        exp.setUser(userService.findUserEntityByUsername(expenseDto.getUsername()));

        Expense savedExpense = expenseRepository.save(exp);
        return expenseMapper.expenseToExpenseDto(savedExpense);
    }

}
