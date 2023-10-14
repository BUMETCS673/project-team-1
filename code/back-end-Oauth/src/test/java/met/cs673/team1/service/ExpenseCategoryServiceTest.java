package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseCategoryServiceTest {

    static final Integer ID = 1;
    static final String NAME_ONE = "category";
    static final String NAME_TWO = "category2";

    @Mock
    ExpenseCategoryRepository categoryRepository;

    @InjectMocks
    ExpenseCategoryService categoryService;

    ExpenseCategory category;

    @BeforeEach
    void setUp() {
        category = new ExpenseCategory();
    }

    @Test
    void testFindByIdSuccess() {
        doReturn(Optional.of(category)).when(categoryRepository).findById(anyInt());
        ExpenseCategory result = categoryService.findById(ID);
        assertNotNull(result);
    }

    @Test
    void testFindByIdThrowsException() {
        doReturn(Optional.empty()).when(categoryRepository).findById(anyInt());
        assertThrows(EntityNotFoundException.class, () -> categoryService.findById(ID));
    }

    @Test
    void testFindByNameSuccess() {
        doReturn(Optional.of(category)).when(categoryRepository).findByName(anyString());
        ExpenseCategory result = categoryService.findByName(NAME_ONE);
        assertNotNull(result);
    }

    @Test
    void testFindByNameThrowsException() {
        doReturn(Optional.empty()).when(categoryRepository).findByName(anyString());
        assertThrows(EntityNotFoundException.class, () -> categoryService.findByName(NAME_ONE));
    }

    @Test
    void testSaveAllCategoriesEmptyList() {
        List<ExpenseCategory> result = categoryService.saveAll(new ArrayList<>());
        assertThat(result).isEmpty();
    }

    @Test
    void testSaveAllCategories() {
        List<String> names = Arrays.asList(NAME_ONE, NAME_TWO);
        ExpenseCategory one = new ExpenseCategory();
        one.setName(NAME_ONE);
        ExpenseCategory two = new ExpenseCategory();
        two.setName(NAME_TWO);
        doReturn(one).doReturn(two).when(categoryRepository).save(any(ExpenseCategory.class));

        List<ExpenseCategory> result = categoryService.saveAll(names);
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(NAME_ONE);
        assertThat(result.get(1).getName()).isEqualTo(NAME_TWO);
    }
}
