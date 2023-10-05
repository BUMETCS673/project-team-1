package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.UserMapper;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static String USERNAME = "username";
    private static String EMAIL = "email";

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void testFindByIdSuccess() {
        User testUser = new User();
        testUser.setUsername(USERNAME);
        testUser.setEmail(EMAIL);

        UserGetDto dto = new UserGetDto();
        dto.setUsername(USERNAME);
        dto.setEmail(EMAIL);

        doReturn(Optional.of(testUser)).when(userRepository).findById(any(Integer.class));
        doReturn(dto).when(userMapper).userToUserGetDto(any(User.class));
        UserGetDto user = userService.findById(Integer.valueOf(1));

        assertNotNull(user);
        assertThat(ReflectionTestUtils.getField(user, USERNAME)).isEqualTo(USERNAME);
        assertThat(ReflectionTestUtils.getField(user, EMAIL)).isEqualTo(EMAIL);
    }

    @Test
    void testFindByIdThrowsException() {
        Optional<User> optUser = Optional.empty();
        doReturn(optUser).when(userRepository).findById(any(Integer.class));
        Integer userId = 1;
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

}
