package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class IncomeControllerTest {

    private static String USERNAME = "user123";
    private static Double INCOME = 1200.0;

    @Mock
    IncomeService incomeService;

    @InjectMocks
    IncomeController incomeController;

    @Test
    void testAddIncome() {
        IncomeDto dto = new IncomeDto();
        doNothing().when(incomeService).addIncome(any(IncomeDto.class));

        ResponseEntity<Void> response = incomeController.addUserIncome(dto);

        verify(incomeService).addIncome(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testGetIncomeByUsername() {
        IncomeDto dto = new IncomeDto();
        dto.setUsername(USERNAME);
        dto.setAmount(INCOME);
        List<IncomeDto> dtos = Arrays.asList(dto);
        doReturn(dtos).when(incomeService).findAllByUsername(anyString());

        ResponseEntity<List<IncomeDto>> response = incomeController.getIncomesByUsername(USERNAME);

        verify(incomeService).findAllByUsername(USERNAME);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getUsername()).isEqualTo(USERNAME);
        assertThat(response.getBody().get(0).getAmount()).isEqualTo(INCOME);
    }
}
