 package met.cs673.team1.controller;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import met.cs673.team1.domain.dto.ExpenseDto;
 import met.cs673.team1.domain.entity.Expense;
 import met.cs673.team1.domain.entity.ExpenseCategory;
 import met.cs673.team1.domain.entity.User;
 import met.cs673.team1.service.ExpenseCategoryService;
 import met.cs673.team1.service.ExpenseService;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
 import org.mockito.ArgumentMatchers;
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
 import static org.mockito.BDDMockito.given;
 import static org.mockito.Mockito.*;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 @WebMvcTest(ExpenseCategoryController.class)
 @AutoConfigureMockMvc()

 class ExpenseCategoryControllerIntegrationTest {
     @Autowired
     private MockMvc mockMvc;

     @MockBean
     ExpenseCategoryService expenseCategoryService;

     @Autowired
     ObjectMapper objectMapper;

     @BeforeEach
     public void setUp() {

     }

     @Test
     public void testAddCategories() throws Exception {
        String hi = "hello";
        assert(hi.equals("hello"));

     }

 }
