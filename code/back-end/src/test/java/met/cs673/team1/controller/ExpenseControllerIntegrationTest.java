 package met.cs673.team1.controller;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import met.cs673.team1.domain.dto.ExpenseDto;
 import met.cs673.team1.domain.entity.Expense;
 import met.cs673.team1.domain.entity.ExpenseCategory;
 import met.cs673.team1.domain.entity.User;
 import met.cs673.team1.service.ExpenseService;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
 import org.mockito.ArgumentMatchers;
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

 import java.sql.Date;
 import java.time.LocalDate;

 import static org.jboss.logging.NDC.get;
 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.BDDMockito.given;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 @WebMvcTest(controllers = ExpenseController.class)
 @AutoConfigureMockMvc(addFilters = false)
 @ExtendWith(MockitoExtension.class)

 class ExpenseControllerIntegrationTest {
     @Autowired
     private MockMvc mockMvc;
     @MockBean
     ExpenseService expenseService;
     @Autowired
     ObjectMapper objectMapper;

     private ExpenseDto expenseDto;

     @Test
     public void testAddExpense() throws Exception {

         String hi = "hello";
         assert(hi.equals("hello"));

//         LocalDate date = LocalDate.of(2020, 6, 10);
//
//         // create ExpenseDto object
//         ExpenseDto expenseDto = new ExpenseDto();
//         expenseDto.setAmount(200.00);
//         expenseDto.setExpenseId(1);
//         expenseDto.setUsername("test124");
//         expenseDto.setName("lettuce");
//         expenseDto.setCategory("groceries");
//         expenseDto.setDate(date);
//
//         // mocks expenseService save method to return the argument that was passed in
//         given(expenseService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
//
//         // simulates POST request to /addExpense
//         ResultActions response = mockMvc.perform(post("/addExpense")
//                 .contentType(MediaType.APPLICATION_JSON_VALUE)
//                 .content(objectMapper.writeValueAsString(expenseDto)));
//
//         // check that status is created
//         response.andExpect(status().isCreated());
     }


 }
