package met.cs673.team1.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.exception.UserNotFoundException;
import met.cs673.team1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MapperUtilTest {

    private static String USERNAME = "username";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MapperUtil mapperUtil;

    @Test
    void testMapUsernameToUser() {
        doReturn(Optional.of(new User())).when(userRepository).findByUsername(anyString());
        User u = mapperUtil.mapUsernameToUser(USERNAME);

        verify(userRepository).findByUsername(USERNAME);
        assertNotNull(u);
    }

    @Test
    void testMapUsernameToUserThrowsException() {
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());
        assertThrows(UserNotFoundException.class, () -> mapperUtil.mapUsernameToUser(USERNAME));
    }
}
