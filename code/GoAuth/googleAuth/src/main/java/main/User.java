package main;

import org.springframework.stereotype.Component;

@Component
public class User implements ExpenseObserver {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {

    }
    public User(String username) {
        this.username = username;
    }

    @Override
    public void update(Expense updatedExpense) {
        System.out.println("User " + username + " received an expense update: " + updatedExpense.getAmount());
    }
}
