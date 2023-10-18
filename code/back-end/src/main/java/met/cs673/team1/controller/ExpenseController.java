package met.cs673.team1.controller;

import jakarta.validation.Valid;

import java.time.Month;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.UserService;
import met.cs673.team1.validation.ValidMonthYear;
import met.cs673.team1.validation.ValidateDateRange;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for dealing with expenses
 */
@RestController
@Validated
@CrossOrigin
public class ExpenseController {

    private ExpenseService expenseService;
    private UserService userService;
    private MonthYearFormatter formatter;

    public ExpenseController(ExpenseService expenseService,
                             UserService userService,
                             MonthYearFormatter formatter) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.formatter = formatter;
    }

    /**
     * Get a list of expenses by user id
     * @param userId id of user
     * @return list of expenses, represented as ExpenseDto objects
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/expenses/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpenses(
            @PathVariable Integer userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<ExpenseDto> expenses;
        if (startDate == null && endDate == null) {
            expenses = expenseService.findAllByUserId(userId);
        } else {
            expenses = expenseService.findAllByUserIdAndDateRange(userId, startDate, endDate);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    /**
     * Get all user expenses within a date range
     * @param username username for the expense search
     * @param startDate beginning of the date range
     * @param endDate end of the date range
     * @return List of expenses
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpenses(
            @RequestParam String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        User user = userService.findUserEntityByUsername(username);
        return getAllUserExpenses(user.getUserId(), startDate, endDate);
    }

    /**
     * Get all user expenses by username and month
     * @param username username for the expense search
     * @param monthYear string representing a month and year
     * @return List of expenses
     */
    @GetMapping(value = "/expenses", params = {"username", "month"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseDto>> getAllUserExpenses(
            @RequestParam String username,
            @RequestParam("month") @ValidMonthYear String monthYear
    ) {
        YearMonth ym = formatter.formatMonthYearString(monthYear);
        return getAllUserExpenses(username, ym.atDay(1), ym.atEndOfMonth());
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

        Double currentMonthlyExp = 0.0;

        currentMonthlyExp = getCurrentMonthlyExp(expenseDto) + result.getAmount();

        if ((budget != null) && (currentMonthlyExp > budget)){
            result.setIsOverBudget(true);
        }else{
            result.setIsOverBudget(false);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //Get the user and their budget from the DB.
    private Double getUserBudget(ExpenseDto expenseDto){
        UserGetDto user = userService.findByUsername(expenseDto.getUsername());
        return  user.getBudget();
    }

    //query expenses table for the user for the month of the expense and return the sum of the amounts.

    private Double getCurrentMonthlyExp(ExpenseDto expenseDto){
        List<ExpenseDto> expenses = expenseService.findAllExpensesByUsername(expenseDto.getUsername());
        Month expenseMonth = expenseDto.getDate().getMonth();
        int expenseYear = expenseDto.getDate().getYear();
        double sum = 0;
        for (ExpenseDto expense : expenses){
            if (expense.getDate().getMonth()  == expenseMonth && expense.getDate().getYear() == expenseYear){
                sum += expense.getAmount();
            }
        }
        
       
        return sum;
    }
}
