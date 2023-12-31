package met.cs673.team1.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.UserMapper;
import met.cs673.team1.repository.RoleRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class providing processing for user data before persistence.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    /**
     * Find a user by their id
     * @param id user id to search for
     * @return Data transfer object representing user's information, password excluded
     * @throws UserNotFoundException
     */
    public UserGetDto findById(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return userMapper.userToUserGetDto(result.get());
        } else {
            throw new UserNotFoundException("No user found");
        }
    }

    public UserGetDto findByUsername(String username) throws UserNotFoundException {
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isPresent()) {
            return userMapper.userToUserGetDto(result.get());
        } else {
            throw new UserNotFoundException("No user found");
        }
    }

    /**
     * Save user to the database
     * @param userPostDto Data transfer object representing user's information
     */
    public UserGetDto save(UserPostDto userPostDto) {
        User user = userMapper.userPostDtoToUser(userPostDto);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserGetDto(savedUser);
    }

    /**
     * Find user entity by user id. This method is used as a utility methods for other services
     * that need access specifically to the User entity, not a UserGetDto.
     * @param id user id for search
     * @return User object
     */
    public User findUserEntityById(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("No user found");
        }
        return optUser.get();
    }

    /**
     * Find user entity by username. Used as a utility method for other services.
     * @param username username for search
     * @return User object
     */
    public User findUserEntityByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username '%s' not found", username));
        }
        return optUser.get();
    }

    /**
     * Update a user's budget in the system
     * @param username username for user search
     * @param amount new budget amount
     * @return UserGetDto, with new budget set
     */
    public UserGetDto updateBudget(String username, Double amount) {
        User user = findUserEntityByUsername(username);
        user.setBudget(amount);
        User saved = userRepository.save(user);
        return userMapper.userToUserGetDto(saved);
    }
}