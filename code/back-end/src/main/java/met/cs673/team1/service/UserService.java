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

    /**
     * Save user to the database
     * @param userPostDto Data transfer object representing user's information
     */
    public void save(UserPostDto userPostDto) {
        User user = userMapper.userPostDtoToUser(userPostDto);
        userRepository.save(user);
    }
}

// if email not exists
// else
// create user


