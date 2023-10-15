package met.cs673.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc
class IncomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MonthYearFormatter formatter;

    @MockBean
    private IncomeService incomeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addUserIncome() throws Exception {
        LocalDate date = LocalDate.of(2021, 10, 14);
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setUsername("test");
        incomeDto.setAmount(1000.0);
        incomeDto.setIncomeId(1);
        incomeDto.setName("check");
        incomeDto.setDate(date);

        // Convert the IncomeDto to JSON
        String jsonRequest = objectMapper.writeValueAsString(incomeDto);

        // Perform a POST request to the "/addIncome" endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/addIncome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }
}