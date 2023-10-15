package met.cs673.team1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@EnableAsync
@Configuration
@EnableRetry
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @Bean
    public DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("MMMyyyy");
    }

    private List<String> authorizedRedirectUris = new ArrayList<>();

    private String tokenSecret;

    private long tokenExpirationMsec;
}
