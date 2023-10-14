package met.cs673.team1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
          securedEnabled = true,
          jsr250Enabled = true,
          prePostEnabled = true
)
/**
public class SecurityConfig  {

     protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .disable()//some stackoverflow solution(not accepted) said so
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/login.html");


            }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .disable()//some stackoverflow solution(not accepted) said so
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login();
                   // .defaultSuccessUrl("/Dashboard.html", true);

        return http.build();
    }

    }

