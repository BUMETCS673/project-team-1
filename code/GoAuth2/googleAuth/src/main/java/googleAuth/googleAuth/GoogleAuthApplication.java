package googleAuth.googleAuth;
import main.Expense;
import main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Map;


@SpringBootApplication
public class GoogleAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleAuthApplication.class, args);
	}
		}

