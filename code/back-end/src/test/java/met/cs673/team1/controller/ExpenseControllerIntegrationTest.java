package met.cs673.team1.controller;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @BeforeEach
    void setUp() {
    }

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

        // simulate HTTP GET request to /expenses/{userId}
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

        // Perform GET request to /expenses
        ResultActions response = mockMvc.perform(get("/expenses")
                .param("username", username)
                .param("startDate", "2023-10-01")
                .param("endDate", "2023-10-31")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

//    @Test
//    public void addExpense() throws Exception {
//        // create mock expense data
//        ExpenseDto expenseDto = new ExpenseDto();
//        expenseDto.setName("Groceries");
//        expenseDto.setExpenseId(1);
//        expenseDto.setUsername("testUser");
//        expenseDto.setCategory("Food");
//        expenseDto.setAmount(200.00);
//        expenseDto.setDate(LocalDate.of(2023, 10, 10 ));
//
//        // mock expenseService to return expense
//        given(expenseService.save(ArgumentMatchers.any()))
//                .willReturn(expenseDto);
//
//        String expenseJson = new ObjectMapper().writeValueAsString(expenseDto);
//
//
//        ResultActions result = mockMvc.perform(post("/addExpense")
//                .content(expenseJson)
//                .contentType(MediaType.APPLICATION_JSON_VALUE));
//
//        result.andExpect(status().isCreated());
//
//    }

}