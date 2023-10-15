package met.cs673.team1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/Welcome", method = RequestMethod.GET)
    //@GetMapping("/")
    public String hellowWorld() {
        return "Welcome to PennyWise";
    }
}
