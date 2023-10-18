package googleauth.googleauth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

 
@RestController
@RequestMapping("/")
public class SampleController {

 @GetMapping
     public RedirectView user(@AuthenticationPrincipal OAuth2User principal) {

    String email = (String) principal.getAttribute("email");
    String name = (String) principal.getAttribute("name");

    Map<String, Object> attributes = new HashMap<>();
    attributes.put("email", email);
    attributes.put("name", name);
    String redirectUrl = "https://pennywise-ui-2c247d5cd417.herokuapp.com/dashboard?email=" + email+ "&name=" + name; // Include the email as a query parameter
    return new RedirectView(redirectUrl,true);

    }
}


