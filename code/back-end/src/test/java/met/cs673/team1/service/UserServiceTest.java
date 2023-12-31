package met.cs673.team1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.mapper.UserMapper;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static Integer USER_ID = 1;
    private static String USERNAME = "username";
    private static String EMAIL = "email";

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    User testUser;
    UserGetDto dto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername(USERNAME);
        testUser.setEmail(EMAIL);

        dto = new UserGetDto();
        dto.setUsername(USERNAME);
        dto.setEmail(EMAIL);
    }

    @Test
    void testFindByIdSuccess() {
        doReturn(Optional.of(testUser)).when(userRepository).findById(anyInt());
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
        assertThrows(UserNotFoundException.class, () -> userService.findById(USER_ID));
    }

    @Test
    void testFindByUsernameSuccess() {
        doReturn(Optional.of(testUser)).when(userRepository).findByUsername(anyString());
        doReturn(dto).when(userMapper).userToUserGetDto(any(User.class));
        UserGetDto user = userService.findByUsername(USERNAME);

        assertNotNull(user);
        assertThat(ReflectionTestUtils.getField(user, USERNAME)).isEqualTo(USERNAME);
        assertThat(ReflectionTestUtils.getField(user, EMAIL)).isEqualTo(EMAIL);
    }

    @Test
    void testFindByUsernameThrowsException() {
        Optional<User> optUser = Optional.empty();
        doReturn(optUser).when(userRepository).findByUsername(anyString());
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(USERNAME));
    }

    @Test
    void testSaveUser() {
        User u = new User();
        UserPostDto postDto = UserPostDto.builder().build();
        UserGetDto getDto = UserGetDto.builder().build();
        doReturn(u).when(userMapper).userPostDtoToUser(any(UserPostDto.class));
        doReturn(u).when(userRepository).save(any(User.class));
        doReturn(getDto).when(userMapper).userToUserGetDto(any(User.class));

        UserGetDto result = userService.save(postDto);

        assertNotNull(result);
        verify(userMapper).userPostDtoToUser(postDto);
        verify(userRepository).save(u);
        verify(userMapper).userToUserGetDto(u);
    }

    @Test
    void testFindUserEntityById() {
        doReturn(Optional.of(testUser)).when(userRepository).findById(anyInt());
        User user = userService.findUserEntityById(USER_ID);

        assertNotNull(user);
        assertThat(ReflectionTestUtils.getField(user, USERNAME)).isEqualTo(USERNAME);
        assertThat(ReflectionTestUtils.getField(user, EMAIL)).isEqualTo(EMAIL);
    }

    @Test
    void testFindUserEntityByIdThrowsException() {
        Optional<User> optUser = Optional.empty();
        doReturn(optUser).when(userRepository).findById(anyInt());
        assertThrows(UserNotFoundException.class, () -> userService.findUserEntityById(USER_ID));
    }

    @Test
    void testFindUserEntityByUsername() {
        doReturn(Optional.of(testUser)).when(userRepository).findByUsername(anyString());
        User user = userService.findUserEntityByUsername(USERNAME);

        assertNotNull(user);
        assertThat(ReflectionTestUtils.getField(user, USERNAME)).isEqualTo(USERNAME);
        assertThat(ReflectionTestUtils.getField(user, EMAIL)).isEqualTo(EMAIL);
    }

    @Test
    void testFindUserEntityByUsernameThrowsException() {
        Optional<User> optUser = Optional.empty();
        doReturn(optUser).when(userRepository).findByUsername(anyString());
        assertThrows(UserNotFoundException.class, () -> userService.findUserEntityByUsername(USERNAME));
    }

    @Test
    void testSetUserBudget() {
        UserService spyService = spy(userService);
        Double budget = 100.0;
        User user = new User();
        user.setUsername(USERNAME);
        user.setBudget(budget);

        doReturn(user).when(spyService).findUserEntityByUsername(anyString());
        doReturn(user).when(userRepository).save(any(User.class));
        doReturn(UserGetDto.builder().username(USERNAME).budget(budget).build()).when(userMapper).userToUserGetDto(any(User.class));

        UserGetDto result = spyService.updateBudget(USERNAME, budget);
        verify(spyService).findUserEntityByUsername(USERNAME);
        verify(userRepository).save(user);
        verify(userMapper).userToUserGetDto(user);

        assertThat(result.getBudget()).isEqualTo(budget);
        assertThat(result.getUsername()).isEqualTo(USERNAME);
    }
}
