package met.cs673.team1.controller;

import jakarta.validation.Valid;
import met.cs673.team1.domain.Expense;
import met.cs673.team1.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(value = "/expenses/{id}")
    public ResponseEntity<List<Expense>> getAllUserExpensesById(@PathVariable Integer id) {
        List<Expense> expenses = expenseService.findAllExpensesByUserId(id);

        HttpStatus status = HttpStatus.OK;

        if (expenses.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(expenses, status);
    }

    @PostMapping(value = "/addExpense")
    public ResponseEntity<Expense> addUserExpense(@Valid @RequestBody Expense expense) {
        Expense result = expenseService.save(expense);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
