package met.cs673.team1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.ExpenseMapper;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.ExpenseRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(final ExpenseRepository expenseRepository,
                          final ExpenseCategoryRepository expenseCategoryRepository,
                          final UserRepository userRepository,
                          final ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }

    public List<Expense> findAllExpensesByUserId(Integer id) {
        List<Expense> expenses = expenseRepository.findAllById(Collections.singleton(id));
        return expenses;
    }

    public ExpenseDto save(ExpenseDto expenseDto) {
        Expense exp = expenseMapper.expenseDtoToExpense(expenseDto);
        Optional<User> optUser = userRepository.findByUsername(expenseDto.getUsername());
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("No user found to link this expense to.");
        }
        exp.setUser(optUser.get());

        Expense savedExpense = expenseRepository.save(exp);
        return expenseMapper.expenseToExpenseDto(savedExpense);
    }
}
