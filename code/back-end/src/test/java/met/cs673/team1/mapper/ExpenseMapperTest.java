package met.cs673.team1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
public class ExpenseMapperTest {

    private static String CATEGORY = "Dining";

    @Mock
    private ExpenseCategoryRepository categoryRepository;

    private ExpenseMapper expenseMapper;

    @BeforeEach
    void setUp() {
        expenseMapper = Mappers.getMapper(ExpenseMapper.class);
        ReflectionTestUtils.setField(expenseMapper, "categoryRepository", categoryRepository);
    }

    @Test
    void testMapCategoryNameToCategory() {
        ExpenseCategory category = new ExpenseCategory();
        ReflectionTestUtils.setField(category, "name", CATEGORY);

        doReturn(Optional.of(category)).when(categoryRepository).findByName(anyString());
        ExpenseCategory returnCategory = expenseMapper.mapCategoryNameToCategory(CATEGORY);

        verify(categoryRepository).findByName(CATEGORY);
        assertNotNull(returnCategory);
        assertThat(returnCategory.getName()).isEqualTo(CATEGORY);
    }

    @Test
    void testMapCategoryNameToCategoryThrowsException() {
        doReturn(Optional.empty()).when(categoryRepository).findByName(anyString());
        assertThrows(EntityNotFoundException.class, () -> expenseMapper.mapCategoryNameToCategory(CATEGORY));
    }
}
