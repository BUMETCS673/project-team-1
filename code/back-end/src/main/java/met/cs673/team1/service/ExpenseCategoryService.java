package met.cs673.team1.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import met.cs673.team1.domain.entity.ExpenseCategory;
import met.cs673.team1.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseCategoryService {

    private ExpenseCategoryRepository repository;


    public ExpenseCategoryService(ExpenseCategoryRepository repository) {
        this.repository = repository;
    }

    public ExpenseCategory findById(Integer id) {
        Optional<ExpenseCategory> optCategory = repository.findById(id);
        if (optCategory.isEmpty()) {
            throw new EntityNotFoundException("Expense category not found");
        }
        return optCategory.get();
    }

    public ExpenseCategory findByName(String name) {
        Optional<ExpenseCategory> optCategory = repository.findByName(name);
        if (optCategory.isEmpty()) {
            throw new EntityNotFoundException("Expense category not found");
        }
        return optCategory.get();
    }
}
