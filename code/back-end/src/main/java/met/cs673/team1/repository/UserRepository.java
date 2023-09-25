package met.cs673.team1.repository;

import java.util.Optional;
import met.cs673.team1.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);
}
