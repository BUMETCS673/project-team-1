package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class IncomeControllerTest {

    private static final String USERNAME = "user123";
    private static final Double INCOME = 1200.0;
    private static final LocalDate DATE = LocalDate.of(2023, 9, 10);

    @Captor
    ArgumentCaptor<LocalDate> dateCaptor;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Mock
    IncomeService incomeService;

    @Mock
    MonthYearFormatter formatter;

    @InjectMocks
    IncomeController incomeController;

    @Test
    void testAddIncome() {
        IncomeDto dto = new IncomeDto();
        doReturn(dto).when(incomeService).addIncome(any(IncomeDto.class));

        ResponseEntity<IncomeDto> response = incomeController.addUserIncome(dto);

        verify(incomeService).addIncome(dto);
        assertTrue(response.hasBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testGetIncomesByUsernameWithoutDateRange() {
        IncomeDto dto = new IncomeDto();
        dto.setUsername(USERNAME);
        dto.setAmount(INCOME);
        List<IncomeDto> dtos = List.of(dto);
        doReturn(dtos).when(incomeService).findAllByUsername(anyString());

        ResponseEntity<List<IncomeDto>> response =
                incomeController.findIncomesByUsername(USERNAME, null, null);

        verify(incomeService).findAllByUsername(USERNAME);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getUsername()).isEqualTo(USERNAME);
        assertThat(response.getBody().get(0).getAmount()).isEqualTo(INCOME);
    }

    @Test
    void testGetIncomeByUsernameAndDateRange() {
        IncomeDto dto = new IncomeDto();
        dto.setUsername(USERNAME);
        dto.setAmount(INCOME);
        List<IncomeDto> dtos = List.of(dto);
        doReturn(dtos).when(incomeService).findAllByUsernameAndDateRange(anyString(), any(LocalDate.class), any(LocalDate.class));

        ResponseEntity<List<IncomeDto>> response =
                incomeController.findIncomesByUsername(USERNAME, DATE, DATE);

        verify(incomeService).findAllByUsernameAndDateRange(USERNAME, DATE, DATE);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getUsername()).isEqualTo(USERNAME);
        assertThat(response.getBody().get(0).getAmount()).isEqualTo(INCOME);
    }

    @Test
    void testFindIncomeByUsernameAndMonth() {
        String monthYear = "jun2023";
        LocalDate start = LocalDate.of(2023, 6, 1);
        LocalDate end = LocalDate.of(2023, 6, 30);
        doReturn(YearMonth.of(2023, 6)).when(formatter).formatMonthYearString(anyString());

        IncomeController spyController = Mockito.spy(incomeController);
        ResponseEntity<List<IncomeDto>> response = spyController.findIncomesByUsernameAndMonth(USERNAME, monthYear);

        verify(spyController).findIncomesByUsername(stringCaptor.capture(), dateCaptor.capture(), dateCaptor.capture());
        assertThat(stringCaptor.getValue()).isEqualTo(USERNAME);

        LocalDate startArg = dateCaptor.getAllValues().get(0);
        LocalDate endArg = dateCaptor.getAllValues().get(1);

        assertThat(startArg.compareTo(start)).isZero();
        assertThat(endArg.compareTo(end)).isZero();
    }
}