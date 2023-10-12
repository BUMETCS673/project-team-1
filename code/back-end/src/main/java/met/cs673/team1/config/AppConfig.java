package met.cs673.team1.config;

import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class AppConfig {

    @Bean
    public DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("MMM yyyy");
    }
}
