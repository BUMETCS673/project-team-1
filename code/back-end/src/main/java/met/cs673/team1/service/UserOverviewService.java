package met.cs673.team1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    @Retryable(retryFor = {InterruptedException.class, ExecutionException.class})
    public UserOverviewDto getUserOverview(Integer id) throws InterruptedException, ExecutionException {
        User user = userService.findUserEntityById(id);

        UserOverviewDto overview = getDtoWithFinancialsByUserId(user.getUserId());

        return overview.toBuilder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Recover
    public UserOverviewDto getDtoRecovery(Integer id) {
        User user = userService.findUserEntityById(id);
        return getDtoWithoutFinancialsByUserId(user.getUserId());
    }

    /**
     * If no financial data is available for this user, return a DTO that only contains
     * their name/email.
     *
     * @param id
     * @return
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
    public UserOverviewDto getDtoWithFinancialsByUserId(Integer id) throws InterruptedException, ExecutionException {
        CompletableFuture<List<IncomeDto>> incomeFuture =
                CompletableFuture.supplyAsync(() -> incomeService.findAllByUserId(id));
        CompletableFuture<List<ExpenseDto>> expenseFuture =
                CompletableFuture.supplyAsync(() -> expenseService.findAllExpensesByUserId(id));
        CompletableFuture<Void> combined = CompletableFuture.allOf(expenseFuture, incomeFuture);

        combined.get();

        return UserOverviewDto.builder()
                .incomes(incomeFuture.get())
                .expenses(expenseFuture.get())
                .balance(calculateBalance(incomeFuture.get(), expenseFuture.get()))
                .build();
    }

    public Double calculateBalance(List<IncomeDto> incomes, List<ExpenseDto> expenses) {
        Double totalIncome = incomes.stream().map(IncomeDto::getAmount).reduce(0.0, (total, amt) -> total + amt);
        Double totalExpenses = expenses.stream().map(ExpenseDto::getAmount).reduce(0.0, (total, amt) -> total + amt);
        return totalIncome - totalExpenses;
    }
}
