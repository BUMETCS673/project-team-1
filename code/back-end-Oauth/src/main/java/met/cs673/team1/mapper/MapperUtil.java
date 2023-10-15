package met.cs673.team1.mapper;

import java.util.Optional;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    private UserRepository userRepository;

    public MapperUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User mapUsernameToUser(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username '%s' not found", username));
        }
        return optUser.get();
    }
}
