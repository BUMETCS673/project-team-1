package met.cs673.team1.controller;

import jakarta.validation.Valid;
import java.util.List;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST endpoints for dealing with expenses
 */
@RestController
@CrossOrigin
public class ExpenseController {

    private ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    /**
     * Get a list of expenses by user id
     * @param userId id of user
     * @return list of expenses, represented as ExpenseDto objects
     */
    @GetMapping(value = "/expenses/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpensesById(@PathVariable Integer userId) {
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUserId(userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping(value = "/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpenseByUsername(@RequestParam String username) {
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUsername(username);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    /**
     * Add an expense to the database, linked with the user specified in the post body
     * @param expenseDto Data transfer object containing username and expense info
     * @return ExpenseDto representing created Expense, with expense_id from database
     */
    @PostMapping(value = "/addExpense", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseDto> addUserExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto result = expenseService.save(expenseDto);
        Double budget = getUserBudget(expenseDto);
        Double currentMonthlyExp = getCurrentMonthlyExp(expenseDto);
        if (currentMonthlyExp > budget){
            result.setIsOverBudget(true);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    //Get the user and their budget from the DB.
    private Double getUserBudget(ExpenseDto expenseDto){
        UserGetDto user = userService.findByUsername(expenseDto.getUsername();
        return  user.getBudget();
        ;
    }

    //query expenses table for the user for the month of the expense and return the sum of the ammounts.

    private Double getCurrentMonthlyExp(ExpenseDto expenseDto){
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUsername(expenseDto.getUsername());
        Month expenseMonth = expenseDto.getDate().getMonth();
        int expenseYear = expenseDto.getDate().getYear();
        Double sum = 0;
        for (ExpenseDto expense : expenses){
            if (expense.getDate().getMonth()  == expenseMonth && expense.getDate().getYear() == expenseYear){
                sum += expense.getAmount();
            } 
        }
        
       
        return sum;
    }
}
