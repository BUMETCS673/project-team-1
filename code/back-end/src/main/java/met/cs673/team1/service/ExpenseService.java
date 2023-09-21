package met.cs673.team1.service;

import met.cs673.team1.domain.Expense;
import met.cs673.team1.domain.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.ExpenseRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private ExpenseCategoryRepository expenseCategoryRepository;
    private UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAllExpensesByUserId(Integer id) {
        List<Expense> expenses = expenseRepository.findAllById(Collections.singleton(id));
        return expenses;
    }

    public Expense save(Expense expense) {
        Optional<User> optUser = userRepository.findById(expense.getUserId());
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("No user found to link this expense to.");
        }
        expense.setUser(optUser.get());
        Expense savedExpense = expenseRepository.save(expense);
        return savedExpense;
    }
}
