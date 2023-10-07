package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserOverviewDto;
import met.cs673.team1.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserOverviewServiceTest.TestConfig.class)
class UserOverviewServiceTest {

    private static final Integer USER_ID = 1;
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final Double[] INCOMES = new Double[]{ 100.0, 253.19, 400.56 };
    private static final Double[] EXPENSES = new Double[]{ 23.45, 446.6, 12.2, 93.44 };
    private static final LocalDate DATE = LocalDate.of(2023, 9, 12);
    private static final Double OFFSET = 0.000001;

    @MockBean
    UserService userService;

    @MockBean
    IncomeService incomeService;

    @MockBean
    ExpenseService expenseService;

    @SpyBean
    UserOverviewService overviewService;

    @Captor
    ArgumentCaptor<Supplier<List<IncomeDto>>> incomeCaptor;
    @Captor
    ArgumentCaptor<Supplier<List<ExpenseDto>>> expenseCaptor;

    List<IncomeDto> incomeList;
    List<ExpenseDto> expenseList;
    Double balance;
    User user;
    UserGetDto userGetDto;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        incomeList = (List<IncomeDto>) IntStream.range(0, INCOMES.length).mapToObj(idx ->
                IncomeDto.builder()
                        .date(DATE)
                        .amount(INCOMES[idx])
                        .name("income" + idx)
                        .build()
        ).toList();

        expenseList = (List<ExpenseDto>) IntStream.range(0, EXPENSES.length).mapToObj(idx ->
                ExpenseDto.builder()
                        .date(DATE)
                        .amount(EXPENSES[idx])
                        .name("expense" + idx)
                        .build()
        ).toList();

        balance = Arrays.stream(INCOMES).reduce(0.0, Double::sum) -
                Arrays.stream(EXPENSES).reduce(0.0, Double::sum);

        user = new User();
        user.setUserId(USER_ID);
        user.setEmail(EMAIL);
        user.setUsername(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        userGetDto = UserGetDto.builder()
                .email(EMAIL)
                .username(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    @Test
    void testGetUserOverviewWithoutDateRange() throws InterruptedException, ExecutionException {
        doReturn(user).when(userService).findUserEntityById(anyInt());
        doReturn(incomeList).when(incomeService).findAllByUserId(anyInt());
        doReturn(expenseList).when(expenseService).findAllByUserId(anyInt());

        UserOverviewDto result = overviewService.getUserOverview(USER_ID, null, null);

        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getUsername()).isEqualTo(EMAIL);
        assertThat(result.getBalance()).isCloseTo(balance, offset(OFFSET));
        assertThat(result.getIncomes()).containsAll(incomeList);
        assertThat(result.getExpenses()).containsAll(expenseList);
    }

    @Test
    void testGetUserOverviewWithDateRange() throws InterruptedException, ExecutionException {
        doReturn(user).when(userService).findUserEntityById(anyInt());
        doReturn(incomeList).when(incomeService)
                .findAllByUserIdAndDateRange(anyInt(), any(LocalDate.class), any(LocalDate.class));
        doReturn(expenseList).when(expenseService)
                .findAllByUserIdAndDateRange(anyInt(), any(LocalDate.class), any(LocalDate.class));

        UserOverviewDto result = overviewService.getUserOverview(USER_ID, DATE, DATE);

        verify(overviewService).getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));

        verify(incomeService, times(0)).findAllByUserId(USER_ID);
        verify(incomeService).findAllByUserIdAndDateRange(USER_ID, DATE, DATE);
        verify(expenseService, times(0)).findAllByUserId(USER_ID);
        verify(expenseService).findAllByUserIdAndDateRange(USER_ID, DATE, DATE);

        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getUsername()).isEqualTo(EMAIL);
        assertThat(result.getBalance()).isCloseTo(balance, offset(OFFSET));
        assertThat(result.getIncomes()).containsAll(incomeList);
        assertThat(result.getExpenses()).containsAll(expenseList);
    }

    @Test
    void testGetDtoWithoutFinancials() {
        doReturn(userGetDto).when(userService).findById(anyInt());

        UserOverviewDto result = overviewService.getDtoWithoutFinancialsByUserId(USER_ID);
        assertNotNull(result);

        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getUsername()).isEqualTo(EMAIL);
        assertThat(result.getBalance()).isZero();
        assertThat(result.getIncomes()).isEmpty();
        assertThat(result.getExpenses()).isEmpty();
    }

    @Test
    void testGetDtoWithFinancials() throws InterruptedException, ExecutionException {
        doReturn(incomeList).when(incomeService).findAllByUserId(anyInt());
        doReturn(expenseList).when(expenseService).findAllByUserId(anyInt());

        UserOverviewDto result = overviewService.getDtoWithFinancialsByUserId(
                USER_ID,
                () -> incomeService.findAllByUserId(USER_ID),
                () -> expenseService.findAllByUserId(USER_ID)
        );

        assertThat(result.getBalance()).isCloseTo(balance, offset(OFFSET));
        assertThat(result.getIncomes()).containsAll(incomeList);
        assertThat(result.getExpenses()).containsAll(expenseList);
    }

    @Test
    void testCalculateBalance() {
        assertThat(overviewService.calculateBalance(incomeList, expenseList)).isCloseTo(balance, offset(OFFSET));
        assertThat(overviewService.calculateBalance(incomeList, new ArrayList<>())).isGreaterThan(balance);
        assertThat(overviewService.calculateBalance(new ArrayList<>(), expenseList)).isLessThan(balance);
    }

    @Test
    void testGetUserOverviewRetry_OneInterruptedException() throws InterruptedException, ExecutionException {
        UserOverviewDto dto = UserOverviewDto.builder().email(EMAIL).build();
        doThrow(InterruptedException.class)
                .doReturn(dto)
                .when(overviewService)
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        doReturn(user).when(userService).findUserEntityById(anyInt());

        UserOverviewDto result = overviewService.getUserOverview(USER_ID, null, null);

        // Supplier lambdas are created internally. We can't verify the exact arguments for this call,
        // so do the next best thing.
        verify(overviewService, times(2))
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        assertThat(result.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    void testGetUserOverviewRetry_TwoInterruptedExceptions() throws InterruptedException, ExecutionException {
        UserOverviewDto dto = UserOverviewDto.builder().email(EMAIL).build();
        doThrow(InterruptedException.class)
                .doThrow(InterruptedException.class)
                .doReturn(dto)
                .when(overviewService)
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        doReturn(user).when(userService).findUserEntityById(anyInt());

        UserOverviewDto result = overviewService.getUserOverview(USER_ID, null, null);

        verify(overviewService, times(3))
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        assertThat(result.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    void testGetUserOverviewRetry_Recovery() throws InterruptedException, ExecutionException {
        doThrow(InterruptedException.class)
                .doThrow(InterruptedException.class)
                .doThrow(InterruptedException.class)
                .when(overviewService)
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        doReturn(user).when(userService).findUserEntityById(anyInt());
        doReturn(userGetDto).when(userService).findById(anyInt());

        UserOverviewDto result = overviewService.getUserOverview(USER_ID, null, null);

        verify(overviewService, times(3))
                .getDtoWithFinancialsByUserId(anyInt(), any(Supplier.class), any(Supplier.class));
        verify(overviewService).getDtoWithoutFinancialsByUserId(USER_ID);

        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    /**
     * This inner config class is used to enable Spring's retry mechanism so that we can test it.
     */
    @TestConfiguration
    @EnableRetry
    static class TestConfig {}
}
