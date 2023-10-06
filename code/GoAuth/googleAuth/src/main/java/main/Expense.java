package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String name;
    private double amount;
    private LocalDate date;
    private ExpenseCategory category;

    private List<ExpenseObserver> observers = new ArrayList<>();

    public Expense(String name, double amount, LocalDate date, ExpenseCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public Expense() {

    }

    public void addObserver(ExpenseObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ExpenseObserver observer) {
        observers.remove(observer);
    }
    public void updateExpense(double newAmount) {
        this.amount = newAmount;
        notifyObservers();
    }
    public void notifyObservers() {
        for (ExpenseObserver observer : observers) {
            observer.update(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public List<ExpenseObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<ExpenseObserver> observers) {
        this.observers = observers;
    }
}
