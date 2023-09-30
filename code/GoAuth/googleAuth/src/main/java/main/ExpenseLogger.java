package main;

import main.Expense;
import main.ExpenseObserver;
import org.springframework.stereotype.Component;

@Component
public class ExpenseLogger implements ExpenseObserver {
    @Override
    public void update(Expense expense) {
        // Log the new expense
        System.out.println("Logging new expense: " + expense.getName());
    }
}