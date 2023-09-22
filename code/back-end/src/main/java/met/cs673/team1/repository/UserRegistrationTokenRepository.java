package met.cs673.team1.repository;

import met.cs673.team1.domain.entity.UserRegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationTokenRepository
        extends JpaRepository<UserRegistrationToken, Integer> {}
