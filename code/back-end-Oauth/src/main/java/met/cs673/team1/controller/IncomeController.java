package met.cs673.team1.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.service.IncomeService;
import met.cs673.team1.validation.ValidMonthYear;
import met.cs673.team1.validation.ValidateDateRange;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for dealing with income
 */
@RestController
@Validated
@CrossOrigin
public class IncomeController {

    private final IncomeService incomeService;
    private final MonthYearFormatter formatter;

    public IncomeController(final IncomeService incomeService,
                            final MonthYearFormatter formatter) {
        this.incomeService = incomeService;
        this.formatter = formatter;
    }

    /**
     * Add a user's income to the database
     * @param incomeDto Data transfer object with username and income information
     * @return HTTP status 201 Created or exception handled by @ControllerAdvice class
     * ExceptionHandlerControllerAdvice
     */
    @PostMapping(value = "/addIncome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncomeDto> addUserIncome(@RequestBody IncomeDto incomeDto) {
        IncomeDto dto = incomeService.addIncome(incomeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Get income by username
     * @param username username of the user in the database
     * @return Response entity containing a list of IncomeDto objects representing all income sources
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/income", params = {"username", "startDate", "endDate"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IncomeDto>> findIncomesByUsername(
            @RequestParam String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<IncomeDto> results;
        if (startDate != null && endDate != null) {
            results = incomeService.findAllByUsernameAndDateRange(username, startDate, endDate);
        } else {
            results = incomeService.findAllByUsername(username);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(value = "/income", params = {"username", "month"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IncomeDto>> findIncomesByUsernameAndMonth(
            @RequestParam String username, @ValidMonthYear @RequestParam(name = "month") String monthYear) {
        YearMonth ym = formatter.formatMonthYearString(monthYear);
        return findIncomesByUsername(username, ym.atDay(1), ym.atEndOfMonth());
    }
}
