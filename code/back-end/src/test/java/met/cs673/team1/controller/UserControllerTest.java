package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static String USERNAME = "user123";

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    void testRetrieveUserByIdSuccess() {
        UserGetDto dto = new UserGetDto();
        dto.setUsername(USERNAME);
        doReturn(dto).when(userService).findById(any(Integer.class));

        ResponseEntity<UserGetDto> response = userController.retrieveUserById(1);
        verify(userService).findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody());
        assertThat(response.getBody().getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void testCreateNewUser() {
        UserPostDto dto = new UserPostDto();
        dto.setUsername(USERNAME);

        doNothing().when(userService).save(any(UserPostDto.class));

        ResponseEntity<Void> response = userController.createNewUser(dto);

        verify(userService).save(dto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
