// package met.cs673.team1.controller;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import static org.hamcrest.Matchers.containsString;

// @WebMvcTest(HelloController.class)
// @AutoConfigureMockMvc
// class HelloControllerIntegrationTest {
//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void testHelloWorld() throws Exception {
//         // simulate HTTP GET request to "/"
//         mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 // check response contains expected message
//                 .andExpect(MockMvcResultMatchers.content().string(containsString("Welcome to PennyWise")));
//     }
// }
