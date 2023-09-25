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

/**
 * REST endpoints for dealing with expenses
 */
@RestController
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Get a list of expenses by user id
     * @param userId id of user
     * @return list of expenses, represented as ExpenseDto objects
     */
    @GetMapping(value = "/expenses/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpensesById(@PathVariable Integer userId) {
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUserId(userId);

        HttpStatus status = HttpStatus.OK;

        if (expenses.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(expenses, status);
    }

    /**
     * Add an expense to the database, linked with the user specified in the post body
     * @param expenseDto Data transfer object containing username and expense info
     * @return ExpenseDto representing created Expense, with expense_id from database
     */
    @PostMapping(value = "/addExpense", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseDto> addUserExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto result = expenseService.save(expenseDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
