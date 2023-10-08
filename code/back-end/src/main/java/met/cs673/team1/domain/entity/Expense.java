package met.cs673.team1.domain.entity;

import java.time.LocalDate;


@Entity
@Table(name = "Expense")
@Getter
@Setter
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Integer expenseId;

    private String name;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ExpenseCategory category;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
