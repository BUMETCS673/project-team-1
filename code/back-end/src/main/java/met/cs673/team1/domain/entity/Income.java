package met.cs673.team1.domain.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Income")
@Getter
@Setter
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;

    private String name;

    private Double amount;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
