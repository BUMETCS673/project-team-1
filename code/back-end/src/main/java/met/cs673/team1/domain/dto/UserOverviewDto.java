package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class UserOverviewDto {

    @JsonProperty
    private String username;

    @JsonProperty
    private String email;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private Double balance;

    @JsonProperty
    private List<IncomeDto> incomes;

    @JsonProperty
    private List<ExpenseDto> expenses;
    
    @JsonProperty
    private Double budget;

}
