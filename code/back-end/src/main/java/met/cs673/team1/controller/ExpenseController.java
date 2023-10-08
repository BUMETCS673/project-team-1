package met.cs673.team1.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
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
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
