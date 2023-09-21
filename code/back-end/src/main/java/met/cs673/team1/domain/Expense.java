package met.cs673.team1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue
    private Integer expenseId;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotNull
    private Double amount;

    @JsonProperty
    @NotNull
    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ExpenseCategory category;

    @JsonProperty
    @NotBlank
    private Date date;

    @JsonProperty
    @Transient
    private Integer userId;

    @JsonProperty
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
