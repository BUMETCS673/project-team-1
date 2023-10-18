package met.cs673.team1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import met.cs673.team1.common.MonthYearFormatter;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserOverviewDto;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.service.UserOverviewService;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc

class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;

    @MockBean
    MonthYearFormatter formatter;

    @MockBean
    private UserOverviewService userOverviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUserOverview() throws Exception {
        // create mock expenseList
        java.util.List<ExpenseDto> mockExpenses = new ArrayList<>();
        ExpenseDto expense1 = new ExpenseDto();
        expense1.setExpenseId(1);
        expense1.setUsername("testUser");
        expense1.setDate(LocalDate.of(2023, 10, 15));
        expense1.setCategory("food");
        expense1.setAmount(20.00);
        expense1.setName("potatoes");
        mockExpenses.add(expense1);

        // create mock incomeList
        java.util.List<IncomeDto> mockIncomes = new ArrayList<>();
        IncomeDto income1 = new IncomeDto();
        income1.setDate(LocalDate.of(2023, 10, 14));
        income1.setIncomeId(1);
        income1.setAmount(100.00);
        income1.setUsername("testUser");
        income1.setName("check");
        mockIncomes.add(income1);

        // create mock UserOverviewDto
        UserOverviewDto userOverviewDto = UserOverviewDto.builder()
                .username("test")
                .email("test@example.com")
                .firstName("testUser")
                .lastName("userLastName")
                .balance(1000.0)
                .incomes(mockIncomes)
                .expenses(mockExpenses)
                .build();

        // mock getUserOverview method to return mock overviewDto
        when(userOverviewService.getUserOverview(anyInt(), any(), any())).thenReturn(userOverviewDto);

        // simulate HTTP GET request to /home/{id}
        ResultActions response = mockMvc.perform(get("/home/1")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-12-31")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void testFindUserByUsername() throws Exception {

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("user");
        roles.add(role);

        // Create a mock user and user data
        UserGetDto mockUser = new UserGetDto();
        mockUser.setUsername("testUser");
        mockUser.setFirstName("testName");
        mockUser.setLastName("testLastName");
        mockUser.setEmail("testUser@test.com");
        mockUser.setUserId(1);
        mockUser.setRoles(roles);

        // Mock the userService to return the mock user when findByUsername is called
        given(userService.findByUsername("testUser")).willReturn(mockUser);

        // Perform a GET request to /user?username=testUser
        ResultActions response = mockMvc.perform(get("/user")
                .param("username", "testUser")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockUser)));
    }

    @Test
    void testFindUserById() throws Exception {

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("user");
        roles.add(role);

        // Create a mock user and user data
        UserGetDto mockUser = new UserGetDto();
        mockUser.setUsername("testUser");
        mockUser.setFirstName("testName");
        mockUser.setLastName("testLastName");
        mockUser.setEmail("testUser@test.com");
        mockUser.setUserId(1);
        mockUser.setRoles(roles);

        // Mock the userService to return the mock user when findByUsername is called
        given(userService.findById(1)).willReturn(mockUser);

        // Perform a GET request to /user?username=testUser
        ResultActions response = mockMvc.perform(get("/user/1")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert the expected status and content
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockUser)));
    }



}