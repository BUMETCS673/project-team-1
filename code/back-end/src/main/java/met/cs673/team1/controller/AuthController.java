package met.cs673.team1.controller;

import java.util.HashMap;
import java.util.Map;
import met.cs673.team1.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/token")
    public ResponseEntity<Map<String, String>> getToken(Authentication auth) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenService.getToken(auth));
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
