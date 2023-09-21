package met.cs673.team1.service;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import met.cs673.team1.domain.Role;
import met.cs673.team1.domain.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.RoleRepository;
import met.cs673.team1.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new UserNotFoundException("No user found");
        }
    }

    public void save(User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoleList()
                .forEach(
                        roleName -> {
                            Optional<Role> role = roleRepository.findByName(roleName);
                            if (role.isPresent()) {
                                roles.add(role.get());
                            }
                        });

        user.setUserRoles(roles);

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
                user.getUserRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList());
    }
}
