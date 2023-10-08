package met.cs673.team1.domain.entity;


@Entity
@Table(name = "ExpenseCategory")
@Getter
@Setter
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    private String name;
}
