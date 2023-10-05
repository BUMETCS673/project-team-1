package met.cs673.team1.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

/**
 * Service class providing processing for expense category data before persistence.
 */
@Service
public class ExpenseCategoryService {

    private ExpenseCategoryRepository repository;


    public ExpenseCategoryService(ExpenseCategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Find an expense category by id
     * @param id category id to search for
     * @return ExpenseCategory object
     */
    public ExpenseCategory findById(Integer id) {
        Optional<ExpenseCategory> optCategory = repository.findById(id);
        if (optCategory.isEmpty()) {
            throw new EntityNotFoundException("Expense category not found");
        }
        return optCategory.get();
    }

    /**
     * Find an expense category by name
     * @param name category name (e.g. "Dining")
     * @return ExpenseCategory
     */
    public ExpenseCategory findByName(String name) {
        Optional<ExpenseCategory> optCategory = repository.findByName(name);
        if (optCategory.isEmpty()) {
            throw new EntityNotFoundException("Expense category not found");
        }
        return optCategory.get();
    }

    public List<ExpenseCategory> saveAll(List<String> categories) {
        return categories.stream().map(name -> {
            ExpenseCategory category = new ExpenseCategory();
            category.setName(name);
            return repository.save(category);
        }).toList();
    }
}
