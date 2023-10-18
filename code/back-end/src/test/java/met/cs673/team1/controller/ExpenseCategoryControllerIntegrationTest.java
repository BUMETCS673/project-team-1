<<<<<<< HEAD
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
 import met.cs673.team1.common.MonthYearFormatter;

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

     @MockBean
     MonthYearFormatter formatter;

     @Autowired
     ObjectMapper objectMapper;

     @BeforeEach
     public void setUp() {

     }

     @Test
     public void testAddCategories() throws Exception {

         List<ExpenseCategory> categories = new ArrayList<>();
         ExpenseCategory category1 = new ExpenseCategory();
         ExpenseCategory category2 = new ExpenseCategory();
         category1.setCategoryId(1);
         category1.setName("Rent");
         category2.setCategoryId(2);
         category2.setName("Pet");


         given(expenseCategoryService.saveAll(ArgumentMatchers.anyList()))
                 .willReturn(categories);

         String categoriesJson = new ObjectMapper().writeValueAsString(categories);


         ResultActions result = mockMvc.perform(post("/addCategories")
                 .content(categoriesJson)
                 .contentType(MediaType.APPLICATION_JSON_VALUE));

         result.andExpect(status().isCreated());
     }

     @Test
     public void getCategories() throws Exception {

         // create mock list of categories
         List<ExpenseCategory> mockCategories = new ArrayList<>();
         ExpenseCategory category1 = new ExpenseCategory();
         ExpenseCategory category2 = new ExpenseCategory();
         category1.setCategoryId(1);
         category1.setName("Rent");
         category2.setCategoryId(2);
         category2.setName("Pet");

        // mock getCategories method to return mockCategories
         given(expenseCategoryService.getCategories())
                 .willReturn(mockCategories);

         // convert to JSON
         String categoriesJson = new ObjectMapper().writeValueAsString(mockCategories);


         // simulate HTTP  GET request to /categories
         ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                 .contentType(MediaType.APPLICATION_JSON));

         // Assert the expected status and content
         response.andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON));

     }


 }
=======
package met.cs673.team1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import met.cs673.team1.common.MonthYearFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    MonthYearFormatter formatter;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testAddCategories() throws Exception {
        // create mock data for List of Category objects
        List<ExpenseCategory> mockCategories = new ArrayList<>();
        ExpenseCategory category1 = new ExpenseCategory();
        ExpenseCategory category2 = new ExpenseCategory();
        category1.setCategoryId(1);
        category1.setName("Rent");
        category2.setCategoryId(2);
        category2.setName("Pet");

        // mock expenseCategoryService to return mockCategories
        given(expenseCategoryService.saveAll(ArgumentMatchers.anyList()))
                .willReturn(mockCategories);


        // covert mockCategories into JSON
        String categoriesJson = new ObjectMapper().writeValueAsString(mockCategories);

        // simulate HTTP POST request to "/addCategories"
        ResultActions result = mockMvc.perform(post("/addCategories")
                .content(categoriesJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // check that result status is created
        result.andExpect(status().isCreated());
    }

    @Test
    public void testGetCategories() throws Exception {
        // create mock data for List of Category objects
        List<ExpenseCategory> categories = new ArrayList<>();
        ExpenseCategory category1 = new ExpenseCategory();
        ExpenseCategory category2 = new ExpenseCategory();
        category1.setCategoryId(1);
        category1.setName("Rent");
        category2.setCategoryId(2);
        category2.setName("Pet");

        // mock expenseCategoryService to return mockCategories
        given(expenseCategoryService.getCategories())
                .willReturn(categories);

        // covert mockCategories into JSON
        String categoriesJson = new ObjectMapper().writeValueAsString(categories);

        // simulate HTTP GET request to "/categories"
        ResultActions result = mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // check that result status is created
        result.andExpect(status().isOk());

        // save Mvc response as string
        MvcResult mvcResult = result.andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        List<ExpenseCategory> responseCategories = new ObjectMapper().readValue(responseContent, new TypeReference<List<ExpenseCategory>>() {
        });

        // Verify that the category names in the response match category names in input
        for (int i = 0; i < categories.size(); i++) {
            String expectedName = categories.get(i).getName();
            String actualName = responseCategories.get(i).getName();
            assertEquals(expectedName, actualName);
        }

    }
    


}
>>>>>>> dev
