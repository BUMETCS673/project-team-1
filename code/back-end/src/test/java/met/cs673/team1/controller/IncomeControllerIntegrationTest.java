package met.cs673.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.User;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.type.TypeReference;




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

    @Test
    public void testFindIncomesByUsername() throws Exception {
        // mock data
        String username = "testUser";
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 31);

        // simulate a GET request to /income with parameters
        ResultActions result = mockMvc.perform(get("/income")
                .param("username", username)
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // check response is Ok
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testFindIncomesByUsernameAndMonth() throws Exception {
        // mock data
        String username = "testUser";

        // mock the MonthYearFormatter to return a valid YearMonth
        String monthYear = "Oct2023";
        YearMonth yearMonth = YearMonth.of(2023, Month.OCTOBER);
        when(formatter.formatMonthYearString(monthYear)).thenReturn(yearMonth);

        // simulate GET request to /income with username and month params
        ResultActions result = mockMvc.perform(get("/income")
                .param("username", username)
                .param("month", monthYear)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // check response is ok
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

    @Test
    public void testInvalidMonthYearRequest() throws Exception {
        String username = "testUser";

        // mock invalid 'monthYear' parameter
        String invalidMonthYear = "invalidMonth";


        // simulate GET request to /expenses with invalid 'monthYear'
        MvcResult result = mockMvc.perform(get("/income")
                        .param("username", username)
                        .param("month", invalidMonthYear)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // convert response to JSON
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseContent, new TypeReference<Map<String, Object>>() {
        });

        // check result is bad request
        assertEquals("BAD_REQUEST", responseMap.get("status"));
    }


}