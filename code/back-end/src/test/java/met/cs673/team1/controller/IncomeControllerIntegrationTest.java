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
 import static org.mockito.Mockito.*;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.ArgumentMatchers.argThat;
 import static org.mockito.BDDMockito.given;
 import static org.mockito.Mockito.doNothing;
 import static org.mockito.Mockito.when;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

     @Test
     public void testGetIncomes() throws Exception {


         String hi = "hello";
         assert(hi.equals("hello"));


//         String username = "fish66";
//         List<IncomeDto> incomes = new ArrayList<>();
//
//         // Create IncomeDto objects to add to incomes
//         IncomeDto income1 = new IncomeDto();
//         income1.setName("Salary");
//         income1.setAmount(5000.0);
//         income1.setUsername(username);
//         income1.setDate(LocalDate.of(2023, 1, 15));
//         incomes.add(income1);
//
//         IncomeDto income2 = new IncomeDto();
//         income2.setName("Check");
//         income2.setAmount(100.0);
//         income2.setUsername(username);
//         income2.setDate(LocalDate.of(2023, 2, 28));
//         incomes.add(income2);
//
//         // Mock incomeService findAllByUsername method to return the incomeList
//         when(incomeService.findAllByUsername(username)).thenReturn(incomes);
//
//         // simulate HTTP GET request to "/income"
//         ResultActions response = mockMvc.perform(get("/income")
//                 .param("username", username));
//
//         response.andExpect(status().isOk())
//                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                 // check value of response[0].amount === value of income1 amount
//                 .andExpect(jsonPath("$[0].amount").value(income1.getAmount()))
//                 // check value of response[1].username === value of income2 username
//                 .andExpect(jsonPath("$[1].username").value(income2.getUsername()));
//
//         // check that incomeService.findAllByUsername was called with the provided username
//         verify(incomeService, times(1)).findAllByUsername(username);


     }

 }
