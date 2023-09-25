package met.cs673.team1.mapper;

import java.util.Optional;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Map between IncomeDto and Income objects before persisting to and after retrieving from the database.
 */
@Mapper(componentModel = "spring")
public abstract class IncomeMapper {

    @Autowired
    UserRepository userRepository;

    @Mapping(source = "username", target = "user")
    public abstract Income incomeDtoToIncome(IncomeDto incomeDto);

    public User mapUsernameToUser(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username '%s' not found", username));
        }
        return optUser.get();
    }

    @Mapping(source = "user", target = "username")
    public abstract IncomeDto incomeToIncomeDto(Income income);

    public String mapUserToUsername(User user) {
        return user.getUsername();
    }
}
