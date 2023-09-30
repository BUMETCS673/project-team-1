package main;

import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements ExpenseObserver {
    @Override
    public void update(Expense expense) {
        // Send email notifications about the new expense
        System.out.println("Sending email notification for expense: " + expense.getName());
    }
}
