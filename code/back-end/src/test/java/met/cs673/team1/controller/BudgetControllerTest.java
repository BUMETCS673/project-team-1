package met.cs673.team1.controller;

import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BudgetControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    BudgetController budgetController;

    @Test
    void testBudgetController() {
        String username = "test";
        Double amount = 100.0;
        UserGetDto dto = UserGetDto.builder().username(username).budget(amount).build();
        doReturn(dto).when(userService).updateBudget(anyString(), anyDouble());
        ResponseEntity<UserGetDto> response = budgetController.setUserBudget(username, amount);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(UserGetDto.class);
        UserGetDto body = response.getBody();
        assertNotNull(body.getUsername());
        assertEquals(username, body.getUsername());
        assertNotNull(body.getBudget());
        assertEquals(amount, body.getBudget());
    }
}