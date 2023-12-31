package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.mapper.IncomeMapper;
import met.cs673.team1.repository.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    static final LocalDate DATE = LocalDate.of(2023, 9, 12);
    static final String USERNAME = "user123";

    @Mock
    IncomeRepository incomeRepository;
    @Mock
    UserService userService;
    @Mock
    IncomeMapper incomeMapper;

    @InjectMocks
    IncomeService incomeService;

    @Test
    void testAddIncome() {
        Income income = new Income();
        IncomeDto dto = IncomeDto.builder().build();
        doReturn(income).when(incomeRepository).save(any(Income.class));
        doReturn(income).when(incomeMapper).incomeDtoToIncome(any(IncomeDto.class));
        doReturn(dto).when(incomeMapper).incomeToIncomeDto(any(Income.class));

        IncomeDto result = incomeService.addIncome(dto);

        assertNotNull(result);
        verify(incomeRepository).save(income);
        verify(incomeMapper).incomeDtoToIncome(dto);
        verify(incomeMapper).incomeToIncomeDto(income);
    }

    @Test
    void testFindAllByUserIdAndDateRange() {
        Integer userId = 1;
        LocalDate date = LocalDate.now();
        doReturn(Arrays.asList(new Income(), new Income()))
                .when(incomeRepository)
                .findAllByUserUserIdAndDateBetween(anyInt(), any(LocalDate.class), any(LocalDate.class));

        List<IncomeDto> incomes = incomeService.findAllByUserIdAndDateRange(userId, date, date);

        assertThat(incomes).hasSize(2);
        verify(incomeRepository).findAllByUserUserIdAndDateBetween(userId, date, date);
        verify(incomeMapper, times(2)).incomeToIncomeDto(any(Income.class));
    }

    @Test
    void testFindAllByUsernameSuccess() {
        IncomeDto dto = new IncomeDto();
        dto.setUsername(USERNAME);
        Integer userId = 1;
        User user = new User();
        user.setUserId(userId);

        doReturn(user).when(userService).findUserEntityByUsername(anyString());
        doReturn(Arrays.asList(new Income(), new Income())).when(incomeRepository).findAllByUserUserId(any(Integer.class));
        doReturn(dto).when(incomeMapper).incomeToIncomeDto(any(Income.class));

        List<IncomeDto> incomes = incomeService.findAllByUsername(USERNAME);

        verify(userService).findUserEntityByUsername(USERNAME);
        verify(incomeRepository).findAllByUserUserId(userId);
        verify(incomeMapper, times(2)).incomeToIncomeDto(any(Income.class));

        assertThat(incomes).hasSize(2);
    }

    @Test
    void testFindAllByUsernameAndDateRange() {
        IncomeDto dto = new IncomeDto();
        dto.setUsername(USERNAME);
        Integer userId = 1;
        User user = new User();
        user.setUserId(userId);

        doReturn(user).when(userService).findUserEntityByUsername(anyString());
        doReturn(Arrays.asList(new Income(), new Income()))
                .when(incomeRepository)
                .findAllByUserUserIdAndDateBetween(any(Integer.class), any(LocalDate.class), any(LocalDate.class));
        doReturn(dto).when(incomeMapper).incomeToIncomeDto(any(Income.class));

        List<IncomeDto> incomes = incomeService.findAllByUsernameAndDateRange(USERNAME, DATE, DATE);

        verify(userService).findUserEntityByUsername(USERNAME);
        verify(incomeRepository).findAllByUserUserIdAndDateBetween(userId, DATE, DATE);
        verify(incomeMapper, times(2)).incomeToIncomeDto(any(Income.class));

        assertThat(incomes).hasSize(2);
    }
}
