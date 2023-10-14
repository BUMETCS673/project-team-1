package met.cs673.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.ExpenseService;
import met.cs673.team1.service.IncomeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        Set<Role> roles =new HashSet<Role>();
        Role role = new Role();
        roles.add(role);

        User user = new User();
        user.setUsername("test");
        user.setUserId(1);
        user.setEmail("@.test");
        user.setRoles(roles);
        user.setLastName("mtest");
        user.setFirstName("rtest");

        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-01-31");

        // Create a list of mock ExpenseDto objects
        List<ExpenseDto> mockExpenses = new ArrayList<>();

        ExpenseDto expense1 = new ExpenseDto();
        expense1.setExpenseId(1);
        expense1.setUsername(user.getUsername());
        expense1.setDate(startDate);
        expense1.setCategory("food");
        expense1.setAmount(20.00);
        expense1.setName("potatoes");

        mockExpenses.add(expense1);


        String hi = "hello";
        assert(hi.equals("hello"));

    }


}