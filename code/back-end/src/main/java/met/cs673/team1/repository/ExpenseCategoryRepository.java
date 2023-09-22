package met.cs673.team1.repository;

import java.util.Optional;
import met.cs673.team1.domain.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {
    Optional<ExpenseCategory> findByName(String name);
}
