package met.cs673.team1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MonthYearFormatter formatter;

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private UserService userService;


    @Test
    void testFindAllByUserId() throws Exception {
        // create mock userId
        Integer userId = 1;

        // create mock dates
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-01-31");

        // create list of mock ExpenseDto objects
        List<ExpenseDto> mockExpenses = new ArrayList<>();

        ExpenseDto expense1 = new ExpenseDto();
        expense1.setExpenseId(userId);
        expense1.setUsername("testUser");
        expense1.setDate(startDate);
        expense1.setCategory("food");
        expense1.setAmount(20.00);
        expense1.setName("potatoes");

        mockExpenses.add(expense1);

        // mock findAllByUserId to return  mockExpenses
        when(expenseService.findAllByUserId(userId)).thenReturn(mockExpenses);

        // simulate GET request to /expenses/{userId}
        ResultActions response = mockMvc.perform(get("/expenses/{userId}", userId)
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-12-31")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllUserExpensesByUsername() throws Exception {
        // create mock userData
        String username = "testUser";
        User user = new User();
        user.setUserId(1);
        user.setUsername(username);

        // create mock expense data
        List<ExpenseDto> expenses = new ArrayList<>();

        // mock userService to return user
        when(userService.findUserEntityByUsername(username)).thenReturn(user);
        // mock expenseService to return expenses
        when(expenseService.findAllByUserId(user.getUserId())).thenReturn(expenses);

        // simulate GET request to /expenses
        ResultActions response = mockMvc.perform(get("/expenses")
                .param("username", username)
                .param("startDate", "2023-10-01")
                .param("endDate", "2023-10-31")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testInvalidDateParam() throws Exception {
            // create mock userData
            String username = "testUser";
            User user = new User();
            user.setUserId(1);
            user.setUsername(username);

            // create mock expense data
            List<ExpenseDto> expenses = new ArrayList<>();

            // mock userService to return user
            when(userService.findUserEntityByUsername(username)).thenReturn(user);
            // mock expenseService to return expenses
            when(expenseService.findAllByUserId(user.getUserId())).thenReturn(expenses);

        // Test with invalid date parameters (e.g., using an invalid format)
        ResultActions invalidDateResponse = mockMvc.perform(get("/expenses")
                .param("username", username)
                .param("startDate", "invalid-date")
                .param("endDate", "2023-10-31")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content for invalid date parameters
        invalidDateResponse.andExpect(status().isBadRequest());
        }

    @Test
    public void testGetAllUserExpensesByUsernameAndMonth() throws Exception {
        // create mock userData
        String username = "testUser";
        User user = new User();
        user.setUserId(1);
        user.setUsername(username);

        // create mock expense data
        List<ExpenseDto> expenses = new ArrayList<>();

        // mock userService to return user
        when(userService.findUserEntityByUsername(username)).thenReturn(user);
        // mock expenseService to return expenses
        when(expenseService.findAllExpensesByUsername(user.getUsername())).thenReturn(expenses);

        // mock the MonthYearFormatter to return a valid YearMonth
        String monthYear = "Oct2023";
        YearMonth yearMonth = YearMonth.of(2023, Month.OCTOBER);
        when(formatter.formatMonthYearString(monthYear)).thenReturn(yearMonth);


        // simulate  GET request to /expenses
        ResultActions response = mockMvc.perform(get("/expenses")
                .param("username", username)
                .param("month", monthYear)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testInvalidMonthYearRequest() throws Exception {
        String username = "testUser";

        // mock invalid 'monthYear' parameter
        String invalidMonthYear = "invalidMonth";

        // mock the userService to return a user
        when(userService.findUserEntityByUsername(username)).thenReturn(new User());

        // simulate GET request to /expenses with invalid 'monthYear'
        MvcResult result = mockMvc.perform(get("/expenses")
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