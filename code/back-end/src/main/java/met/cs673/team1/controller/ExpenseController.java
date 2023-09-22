package met.cs673.team1.controller;

import jakarta.validation.Valid;
import java.util.List;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping(value = "/expenses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Expense>> getAllUserExpensesById(@PathVariable Integer id) {
        List<Expense> expenses = expenseService.findAllExpensesByUserId(id);

        HttpStatus status = HttpStatus.OK;

        if (expenses.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(expenses, status);
    }

    @PostMapping(value = "/addExpense", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseDto> addUserExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto result = expenseService.save(expenseDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
