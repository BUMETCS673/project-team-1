package met.cs673.team1.repository;

import java.util.List;
import met.cs673.team1.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findAllByUserUserId(Integer userId);
}
