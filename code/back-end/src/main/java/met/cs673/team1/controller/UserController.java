package met.cs673.team1.controller;

import jakarta.validation.Valid;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints related to user operations
 */
@RestController
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a user by id
     * @param id user id
     * @return Response entity containing user data transfer object
     */
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetDto> retrieveUserById(@PathVariable Integer id) {
        UserGetDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create a new user
     * @param userPostDto data transfer object representing the new user's information
     * @return HTTP status 201 Created
     */
    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewUser(@Valid @RequestBody UserPostDto userPostDto) {
        userService.save(userPostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
