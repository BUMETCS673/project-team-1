package met.cs673.team1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ExpenseCategory")
public class ExpenseCategory {

    @Id
    @GeneratedValue
    private Integer expenseId;

    @JsonProperty
    @NotBlank
    private String name;
}
