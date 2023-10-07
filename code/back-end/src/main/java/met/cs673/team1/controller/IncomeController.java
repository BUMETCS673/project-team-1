package met.cs673.team1.controller;

import java.time.LocalDate;
import java.util.List;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.service.IncomeService;
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
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(final IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /**
     * Add a user's income to the database
     * @param incomeDto Data transfer object with username and income information
     * @return HTTP status 201 Created or exception handled by @ControllerAdvice class
     * ExceptionHandlerControllerAdvice
     */
    @PostMapping(value = "/addIncome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserIncome(@RequestBody IncomeDto incomeDto) {
        incomeService.addIncome(incomeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Get income by username
     * @param username username of the user in the database
     * @return Response entity containing a list of IncomeDto objects representing all income sources
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
