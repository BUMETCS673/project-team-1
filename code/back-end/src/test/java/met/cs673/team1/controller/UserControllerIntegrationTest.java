// package met.cs673.team1.controller;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import met.cs673.team1.domain.dto.UserGetDto;
// import met.cs673.team1.domain.dto.UserOverviewDto;
// import met.cs673.team1.domain.dto.UserPostDto;
// import met.cs673.team1.domain.entity.Role;
// import met.cs673.team1.service.UserOverviewService;
// import met.cs673.team1.service.UserService;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.concurrent.ExecutionException;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(UserController.class)
// @AutoConfigureMockMvc
// @ExtendWith(SpringExtension.class)
// class UserControllerIntegrationTest {
//     @Autowired
//     private MockMvc mockMvc;


//     @MockBean
//     private UserService userService;

//     @MockBean
//     private UserOverviewService userOverviewService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     void testFindUserByUsername() throws Exception {
//         List<Role> roles = new ArrayList();

//         // create userGetDTO object
//         UserGetDto userGetDto = new UserGetDto();
//         userGetDto.setUsername("fish66");
//         userGetDto.setUserId(1);
//         userGetDto.setEmail("fish@test.com");
//         userGetDto.setFirstName("fish");
//         userGetDto.setLastName("rivera");
//         userGetDto.setRoles(roles);

//         // Mock userService findByUsername method to return userGetDto object
//         given(userService.findByUsername("fish66")).willReturn(userGetDto);

//         // simulate HTTP GET Request to "/user"
//         mockMvc.perform(get("/user").param("username", "fish66"))
//                 .andExpect(status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                 // compares userId from JSON to userId of userGetDto object
//                 .andExpect(jsonPath("$.userId").value(userGetDto.getUserId()));
//     }


//     @Test
//     void testCreateNewUser() throws Exception {
//         List<String> roles = new ArrayList();
//         roles.add("USER");

//         // create userPostDTO object
//         UserPostDto userPostDto = new UserPostDto();
//         userPostDto.setFirstName("mali");
//         userPostDto.setLastName("rivera");
//         userPostDto.setUsername("newUser");
//         userPostDto.setEmail("test@test.com");
//         userPostDto.setRoles(roles);

//         // Mock the userService save method
//         Mockito.doNothing().when(userService).save(any(UserPostDto.class));

//         // simulate HTTP POST request "/createUser"
//         mockMvc.perform(post("/createUser")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(userPostDto)))
//                 // check status is created
//                 .andExpect(status().isCreated());
//     }


// }
