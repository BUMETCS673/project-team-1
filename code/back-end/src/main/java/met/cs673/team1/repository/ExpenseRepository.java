package met.cs673.team1.repository;

import met.cs673.team1.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findAllByUserUserId(Integer userId);
}
