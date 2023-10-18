package met.cs673.team1.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserOverviewDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.UserOverviewService;
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
 * REST endpoints related to user operations
 */
@RestController
@Slf4j
@Validated
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserOverviewService overviewService;
    private final MonthYearFormatter formatter;

    public UserController(UserService userService,
                          UserOverviewService overviewService,
                          MonthYearFormatter formatter) {
        this.userService = userService;
        this.overviewService = overviewService;
        this.formatter = formatter;
    }

    /**
     * Get a user overview containing all user incomes and expenses, with optional date range.
     * @param id user id to user for search
     * @param startDate optional, beginning of date range
     * @param endDate optional, end of date range
     * @return UserOverviewDto representing all user info based on the search parameters
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/home/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOverviewDto> loadHomePage(
            @PathVariable Integer id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws InterruptedException, ExecutionException {
        UserOverviewDto overviewDto = overviewService.getUserOverview(id, startDate, endDate);
        return ResponseEntity.ok(overviewDto);
    }

    /**
     * Get a user overview by username, with optional date range.
     * @param username username for search
     * @param startDate optional, beginning of date range
     * @param endDate optional, end of date range
     * @return UserOverviewDto with all incomes and expenses
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @ValidateDateRange(start = "startDate", end = "endDate")
    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOverviewDto> loadHomePage(
            @RequestParam String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws InterruptedException, ExecutionException {
        User u = userService.findUserEntityByUsername(username);
        return loadHomePage(u.getUserId(), startDate, endDate);
    }

    /**
     * Get user overview by month and year
     * @param username username for search
     * @param monthYear string representing a month and a year (e.g. "jun2023")
     * @return UserOverviewDto
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @GetMapping(value = "/home", params = {"username", "month"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOverviewDto> loadHomePage(
            @RequestParam String username,
            @RequestParam("month") @ValidMonthYear String monthYear
    ) throws InterruptedException, ExecutionException {
        YearMonth ym = formatter.formatMonthYearString(monthYear);
        return loadHomePage(username, ym.atDay(1), ym.atEndOfMonth());
    }

    /**
     * Get a user by id
     * @param id user id
     * @return Response entity containing user data transfer object
     */
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetDto> retrieveUserById(@PathVariable Integer id) {
        UserGetDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Find a user by username
     * @param username username for search
     * @return UserGetDto representing user information (excluding income/expenses)
     */
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetDto> findUserByUsername(@RequestParam String username) {
        UserGetDto user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create a new user
     * @param userPostDto data transfer object representing the new user's information
     * @return HTTP status 201 Created
     */
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetDto> createNewUser(@Valid @RequestBody UserPostDto userPostDto) {
        UserGetDto dto = userService.save(userPostDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
