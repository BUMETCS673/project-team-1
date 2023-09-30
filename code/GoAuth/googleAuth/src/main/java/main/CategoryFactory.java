package main;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CategoryFactory {
    public Expense createExpense(String name, double amount, LocalDate date, ExpenseCategory category) {
        Expense expense = new Expense(name, amount, date, category);
        expense.notifyObservers(); // Notify observers when an expense is created
        return expense;
    }
}
