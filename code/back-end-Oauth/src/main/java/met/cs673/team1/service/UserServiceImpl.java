package met.cs673.team1.service;

import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.ResourceNotFoundException;
import met.cs673.team1.mapper.UserMapper;
import met.cs673.team1.repository.RoleRepository;
import met.cs673.team1.repository.UserRepository;
import met.cs673.team1.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class UserServiceImpl extends UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, UserRepository userRepository1) {
        super(userRepository, roleRepository, userMapper);
        this.userRepository = userRepository1;
    }

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
