package met.cs673.team1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserOverviewDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.service.UserOverviewService;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    static final String USERNAME = "user123";
    static final LocalDate DATE = LocalDate.now();

    @Mock
    UserService userService;

    @Mock
    UserOverviewService overviewService;

    @InjectMocks
    UserController userController;

    @Test
    void testLoadHomePageWithId() throws InterruptedException, ExecutionException {
        Integer userId = 1;
        doReturn(UserOverviewDto.builder().build())
                .when(overviewService)
                .getUserOverview(anyInt(), any(LocalDate.class), any(LocalDate.class));

        ResponseEntity<UserOverviewDto> response = userController.loadHomePage(userId, DATE, DATE);

        verify(overviewService).getUserOverview(userId, DATE, DATE);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testLoadHomePageWithUsername() throws InterruptedException, ExecutionException {
        UserController spyController = Mockito.spy(userController);
        Integer userId = 1;
        User u = new User();
        u.setUserId(userId);
        doReturn(u).when(userService).findUserEntityByUsername(anyString());
        doReturn(ResponseEntity.ok(UserOverviewDto.builder().build()))
                .when(spyController)
                .loadHomePage(anyInt(), any(LocalDate.class), any(LocalDate.class));

        ResponseEntity<UserOverviewDto> response = spyController.loadHomePage(USERNAME, DATE, DATE);

        verify(userService).findUserEntityByUsername(USERNAME);
        verify(spyController).loadHomePage(userId, DATE, DATE);
        assertTrue(response.hasBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

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

        doReturn(UserGetDto.builder().build()).when(userService).save(any(UserPostDto.class));

        ResponseEntity<UserGetDto> response = userController.createNewUser(dto);

        verify(userService).save(dto);
        assertTrue(response.hasBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
