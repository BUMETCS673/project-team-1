package met.cs673.team1.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class IncomeDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer incomeId;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    @NotNull
    private Double amount;

    @JsonProperty
    @NotBlank
    private String username;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeDto incomeDto = (IncomeDto) o;
        return Objects.equals(name, incomeDto.name) &&
                Objects.equals(amount, incomeDto.amount) &&
                Objects.equals(username, incomeDto.username) &&
                Objects.equals(date, incomeDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, username, date);
    }
}
