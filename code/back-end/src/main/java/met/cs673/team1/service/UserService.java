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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository,
                       final RoleRepository roleRepository,
                       final UserMapper userMapper,
                       final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserGetDto findById(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return userMapper.userToUserGetDto(result.get());
        } else {
            throw new UserNotFoundException("No user found");
        }
    }

    /**
     * Save user to the database, with password hash in place of password.
     * @param userPostDto
     */
    public void save(UserPostDto userPostDto) {
        String encodedPassword = passwordEncoder.encode(userPostDto.getPassword());
        userPostDto.setPassword(encodedPassword);
        User user = userMapper.userPostDtoToUser(userPostDto);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        User user = optUser.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList());
    }
}
