package met.cs673.team1.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "ExpenseCategory")
@Getter
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    private String name;
}
