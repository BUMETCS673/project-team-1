package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ExpenseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer expenseId;

    @JsonProperty
    @NotBlank
    private String username;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotNull
    private Double amount;

    @JsonProperty
    @NotBlank
    private String category;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    
    @JsonProperty
    @NotBlank
    @Builder.Default
     Boolean isOverBudget = false; 

}
