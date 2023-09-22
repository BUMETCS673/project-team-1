package met.cs673.team1.mapper;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import met.cs673.team1.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

    @Autowired
    ExpenseCategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

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
    public User mapUsernameToUser(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new EntityNotFoundException(String.format("User '%s' not found", username));
        }
        return optUser.get();
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
