package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.IncomeMapper;
import met.cs673.team1.repository.IncomeRepository;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    IncomeRepository incomeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    IncomeMapper incomeMapper;

    @InjectMocks
    IncomeService incomeService;

    @Test
    void testAddIncome() {
        Income income = new Income();
        doReturn(income).when(incomeMapper).incomeDtoToIncome(any(IncomeDto.class));

        incomeService.addIncome(new IncomeDto());

        verify(incomeRepository).save(income);
    }

    @Test
    void testFindAllByUsernameSuccess() {
        String username = "user123";
        IncomeDto dto = new IncomeDto();
        dto.setUsername(username);
        Integer userId = 1;
        User user = new User();
        user.setUserId(userId);

        doReturn(Optional.of(user)).when(userRepository).findByUsername(anyString());
        doReturn(Arrays.asList(new Income(), new Income())).when(incomeRepository).findAllByUserUserId(any(Integer.class));
        doReturn(dto).when(incomeMapper).incomeToIncomeDto(any(Income.class));

        List<IncomeDto> incomes = incomeService.findAllByUsername(username);

        verify(userRepository).findByUsername(username);
        verify(incomeRepository).findAllByUserUserId(userId);
        verify(incomeMapper, times(2)).incomeToIncomeDto(any(Income.class));

        assertThat(incomes.size()).isEqualTo(2);
    }

    @Test
    void testFindAllByUsernameException() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());
        assertThrows(UserNotFoundException.class, () -> incomeService.findAllByUsername("username"));
    }
}
