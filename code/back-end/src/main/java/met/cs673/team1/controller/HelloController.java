package met.cs673.team1.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    //@GetMapping("/")
    public String hellowWorld() {
        return "Welcome to PennyWise";
    }

    @PostMapping("/v1/oauth/login")
    public void login() {}
}
