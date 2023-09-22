package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPostDto {

    @JsonProperty
    @NotBlank
    private String username;

    @JsonProperty
    @NotBlank
    private String password;

    @JsonProperty
    @NotBlank
    @Email
    private String email;

    @JsonProperty
    @NotBlank
    private String firstName;

    @JsonProperty
    @NotBlank
    private String lastName;

    @JsonProperty
    @NotEmpty
    private List<String> roles;
}
