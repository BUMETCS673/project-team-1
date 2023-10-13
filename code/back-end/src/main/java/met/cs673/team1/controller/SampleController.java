package met.cs673.team1.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SampleController {

    @PostMapping("/v1/oauth/login")
    public void login() {}

    @GetMapping
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("email", principal.getAttribute("email"));
    }
    @GetMapping("user")
    public Map<String, Object>  currentUser(OAuth2AuthenticationToken OAuth2AuthenticationToken) {
        return OAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

}