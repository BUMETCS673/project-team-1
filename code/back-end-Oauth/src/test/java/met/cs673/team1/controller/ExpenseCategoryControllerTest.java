package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.service.ExpenseCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryControllerTest {

    @Mock
    ExpenseCategoryService categoryService;

    @InjectMocks
    ExpenseCategoryController categoryController;

    @Test
    void testAddCategories() {
        String nameOne = "one";
        String nameTwo = "two";
        List<String> categories = Arrays.asList(nameOne, nameTwo);

        ExpenseCategory one = new ExpenseCategory();
        one.setName(nameOne);
        ExpenseCategory two = new ExpenseCategory();
        one.setName(nameTwo);

        doReturn(Arrays.asList(one, two)).when(categoryService).saveAll(any(List.class));
        ResponseEntity<List<ExpenseCategory>> response = categoryController.addCategories(categories);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).hasSize(2);
    }
}
