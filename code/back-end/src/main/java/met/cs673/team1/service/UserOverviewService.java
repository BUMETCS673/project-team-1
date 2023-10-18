package met.cs673.team1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserOverviewDto;
import met.cs673.team1.domain.entity.User;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserOverviewService {

    private final UserService userService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public UserOverviewService(UserService userService,
                               IncomeService incomeService,
                               ExpenseService expenseService) {
        this.userService = userService;
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    /**
     * Get all user data for the user overview. This method is retryable, and will be retried twice
     * more if there are any exceptions while it is running.
     * @param id user id for search
     * @param start start of date range
     * @param end end of date range
     * @return UserOverviewDto
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Retryable(retryFor = {InterruptedException.class, ExecutionException.class})
    public UserOverviewDto getUserOverview(Integer id, LocalDate start, LocalDate end) throws InterruptedException, ExecutionException {
        User user = userService.findUserEntityById(id);

        Supplier<List<IncomeDto>> incomeSupplier;
        Supplier<List<ExpenseDto>> expenseSupplier;

        // Should we search for incomes and expenses with a date range or without a date range?
        if (start != null && end != null) {
            incomeSupplier = () -> incomeService.findAllByUserIdAndDateRange(id, start, end);
            expenseSupplier = () -> expenseService.findAllByUserIdAndDateRange(id, start, end);
        } else {
            incomeSupplier = () -> incomeService.findAllByUserId(id);
            expenseSupplier = () -> expenseService.findAllByUserId(id);
        }

        UserOverviewDto overview = getDtoWithFinancialsByUserId(user.getUserId(), incomeSupplier, expenseSupplier);

        return overview.toBuilder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    /**
     * Recover method for retryable user overview retrieval.
     * @param id user id
     * @param start not used, but needed to match method parameters of getUserOverview
     * @param end not used, but needed to match method parameters of getUserOverview
     * @return UserOverviewDto without any financial data
     */
    @Recover
    public UserOverviewDto getDtoRecovery(Integer id, LocalDate start, LocalDate end) {
        User user = userService.findUserEntityById(id);
        return getDtoWithoutFinancialsByUserId(user.getUserId());
    }

    /**
     * If no financial data is available for this user, return a DTO that only contains
     * their name/email.
     * @param id user id for search
     * @return UserOverviewDto
     */
    public UserOverviewDto getDtoWithoutFinancialsByUserId(Integer id) {
        UserGetDto dto = userService.findById(id);
        return UserOverviewDto.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .balance(0.0)
                .incomes(new ArrayList<>())
                .expenses(new ArrayList<>())
                .build();
    }

    /**
     * Create a new UserOverviewDto and populate it with financial information for the user
     * represented by the given user id
     * @param id user id to reference
     * @return UserOverviewDto
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public UserOverviewDto getDtoWithFinancialsByUserId(
            Integer id,
            Supplier<List<IncomeDto>> incomeSupplier,
            Supplier<List<ExpenseDto>> expenseSupplier
    ) throws InterruptedException, ExecutionException {

        CompletableFuture<List<IncomeDto>> incomeFuture =
                CompletableFuture.supplyAsync(incomeSupplier);
        CompletableFuture<List<ExpenseDto>> expenseFuture =
                CompletableFuture.supplyAsync(expenseSupplier);
        CompletableFuture<Void> combined = CompletableFuture.allOf(expenseFuture, incomeFuture);

        combined.get();

        return UserOverviewDto.builder()
                .incomes(incomeFuture.get())
                .expenses(expenseFuture.get())
                .balance(calculateBalance(incomeFuture.get(), expenseFuture.get()))
                .build();
    }

    /**
     * Calculate a user's balance based on their incomes and expenses
     * @param incomes list of incomes
     * @param expenses list of expenses
     * @return Double representing total balance
     */
    public Double calculateBalance(List<IncomeDto> incomes, List<ExpenseDto> expenses) {
        Double totalIncome = incomes.stream().map(IncomeDto::getAmount).reduce(0.0, (total, amt) -> total + amt);
        Double totalExpenses = expenses.stream().map(ExpenseDto::getAmount).reduce(0.0, (total, amt) -> total + amt);
        return totalIncome - totalExpenses;
    }
}
