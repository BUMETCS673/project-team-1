package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import met.cs673.team1.domain.entity.Role;

@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class UserGetDto {

    @JsonProperty
    private Integer userId;

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
    private Double budget;

}
