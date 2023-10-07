package met.cs673.team1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.domain.entity.User;
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
    private static String NAME = "Lunch";
    private static Double AMOUNT = 24.5;
    private static String USERNAME = "username";
    private static LocalDate DATE = LocalDate.of(2023, 9, 12);

    @Mock
    private ExpenseCategoryRepository categoryRepository;

    @Mock
    private MapperUtil mapperUtil;

    private ExpenseMapper expenseMapper;

    @BeforeEach
    void setUp() {
        expenseMapper = Mappers.getMapper(ExpenseMapper.class);
        ReflectionTestUtils.setField(expenseMapper, "categoryRepository", categoryRepository);
        ReflectionTestUtils.setField(expenseMapper, "mapperUtil", mapperUtil);
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

    @Test
    void testMapNullExpenseToExpenseDto() {
        ExpenseDto dto = expenseMapper.expenseToExpenseDto(null);
        assertNull(dto);
    }

    @Test
    void testMapExpenseToExpenseDto() {
        User u = new User();
        u.setUsername(USERNAME);
        ExpenseCategory cat = new ExpenseCategory();
        cat.setName(CATEGORY);

        Expense exp = new Expense();
        exp.setUser(u);
        exp.setCategory(cat);
        exp.setDate(DATE);
        exp.setAmount(AMOUNT);
        exp.setName(NAME);

        ExpenseDto dto = expenseMapper.expenseToExpenseDto(exp);
        assertThat(dto.getCategory()).isEqualTo(CATEGORY);
        assertThat(dto.getAmount()).isEqualTo(AMOUNT);
        assertThat(dto.getDate()).isEqualTo(DATE);
        assertThat(dto.getName()).isEqualTo(NAME);
    }

    @Test
    void testNullExpenseDtoToExpense() {
        Expense exp = expenseMapper.expenseDtoToExpense(null);
        assertNull(exp);
    }

    @Test
    void testExpenseDtoToExpense() {
        ExpenseCategory cat = new ExpenseCategory();
        cat.setName(CATEGORY);
        doReturn(Optional.of(cat)).when(categoryRepository).findByName(anyString());

        User u = new User();
        u.setUsername(USERNAME);
        doReturn(u).when(mapperUtil).mapUsernameToUser(anyString());

        ExpenseDto dto = ExpenseDto.builder()
                .name(NAME)
                .amount(AMOUNT)
                .category(CATEGORY)
                .username(USERNAME)
                .date(DATE)
                .build();

        Expense exp = expenseMapper.expenseDtoToExpense(dto);

        assertThat(exp.getAmount()).isEqualTo(AMOUNT);
        assertThat(exp.getName()).isEqualTo(NAME);
        assertThat(exp.getCategory()).isEqualTo(cat);
        assertThat(exp.getDate().compareTo(DATE)).isZero();
        assertNotNull(exp.getUser());
        assertThat(exp.getUser().getUsername()).isEqualTo(USERNAME);
    }
}
