package met.cs673.team1.controller;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@RestController
@RequestMapping
public class OAuth2LoginController {
    @GetMapping("/")
    public RedirectView redirect(Principal principal) {
        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            String email = oAuth2User.getAttribute("email"); // Assuming "email" is the attribute name in your user profile
            String redirectUrl = "http://localhost:3000?email=" + email; // Include the email as a query parameter
            return new RedirectView(redirectUrl);
        } else {
            // Handle the case where the user's email is not available
            return new RedirectView("http://localhost:3000/dashboard");
        }
    }
}

/**
 *
 @GetMapping("/")
 public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
 return Collections.singletonMap("email", principal.getAttribute("email"));
 }
 @GetMapping("UserInfo")
 public Map<String, Object>  currentUser(OAuth2AuthenticationToken OAuth2AuthenticationToken) {
 return OAuth2AuthenticationToken.getPrincipal().getAttributes();
 }

 */
