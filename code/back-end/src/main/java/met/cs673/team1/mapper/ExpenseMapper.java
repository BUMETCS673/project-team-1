package met.cs673.team1.mapper;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Map between ExpenseDto and Expense objects before persisting to and after retrieving from the database.
 */
@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

    @Autowired
    private ExpenseCategoryRepository categoryRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Mapping(source = "username", target = "user", qualifiedByName = "usernameToUser")
    public abstract Expense expenseDtoToExpense(ExpenseDto expenseDto);

    public ExpenseCategory mapCategoryNameToCategory(String category) {
        Optional<ExpenseCategory> optCategory = categoryRepository.findByName(category);
        if (optCategory.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Expense category '%s' not found", category));
        }
        return optCategory.get();
    }

    /**
     * Does this need to be changed? User password hash exposed?
     */
    @Named("usernameToUser")
    public User mapUsernameToUser(String username) {
        return mapperUtil.mapUsernameToUser(username);
    }

    @Mapping(source = "user", target = "username")
    public abstract ExpenseDto expenseToExpenseDto(Expense expense);

    public String mapExpenseCategoryToString(ExpenseCategory expenseCategory) {
        return expenseCategory.getName();
    }

    public String mapUserToUsername(User user) {
        return user.getUsername();
    }
}
