package met.cs673.team1.controller;

import com.diffplug.spotless.maven.json.Json;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import met.cs673.team1.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
