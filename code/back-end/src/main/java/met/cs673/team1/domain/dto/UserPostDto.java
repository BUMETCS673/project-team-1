package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserPostDto {

    @JsonProperty
    @NotBlank
    private String username;

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

    @JsonProperty
    private Double budget;
}
