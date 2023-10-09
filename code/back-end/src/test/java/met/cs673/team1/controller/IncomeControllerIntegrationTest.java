package met.cs673.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.jboss.logging.NDC.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc
class IncomeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IncomeService incomeService;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        doNothing().when(incomeService).addIncome(any(IncomeDto.class));
    }

    @Test
    public void testAddIncome() throws Exception {
        LocalDate date = LocalDate.of(2020, 6, 10);

        // create IncomeDto object
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(100.00);
        incomeDto.setName("check");
        incomeDto.setUsername("fish66");
        incomeDto.setDate(date);

        //simulate HTTP POST to /addIncome
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/addIncome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // check that the addIncome method was called with incomeDto
        Mockito.verify(incomeService).addIncome(incomeDto);
    }

}
