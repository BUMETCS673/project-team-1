package googleauth.googleauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@ComponentScan(basePackages = "main")

public class GoogleAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoogleAuthApplication.class, args);
	}
}

