package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.Role;

@NoArgsConstructor
@Setter
@Getter
public class UserGetDto {

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private List<Role> roles;

    @JsonProperty
    private List<Expense> expenses;
}
