package met.cs673.team1.repository;

import java.util.List;
import met.cs673.team1.domain.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    public List<Income> findAllByUserUserId(Integer id);
}
