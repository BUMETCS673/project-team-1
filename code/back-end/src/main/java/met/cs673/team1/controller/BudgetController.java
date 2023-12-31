package met.cs673.team1.controller;

import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class BudgetController {

    private final UserService userService;

    public BudgetController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method will set a user's budget in the system
     * @param username username of the user in question
     * @param amount double representing their total budget
     * @return UserGetDto representing the user, with the budget field populated
     */
    @GetMapping(value = "/setUserBudget", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetDto> setUserBudget(@RequestParam String username, @RequestParam Double amount) {
        return ResponseEntity.ok(userService.updateBudget(username, amount));
    }
}
