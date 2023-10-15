package met.cs673.team1.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceOauth {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(String id);
}
