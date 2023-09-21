package met.cs673.team1.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import met.cs673.team1.domain.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;

    @InjectMocks UserService userService;

    @Test
    void testFindByIdSuccess() {
        Optional<User> optUser = Optional.of(new User());
        doReturn(optUser).when(userRepository).findById(any(Integer.class));
        User user = userService.findById(Integer.valueOf(1));

        assertNotNull(user);
    }

    @Test
    void testFindByIdThrowsException() {
        Optional<User> optUser = Optional.empty();
        doReturn(optUser).when(userRepository).findById(any(Integer.class));
        Integer userId = 1;
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }
}
