package met.cs673.team1.mapper;

import javax.annotation.processing.Generated;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T22:00:46-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class ExpenseMapperImpl extends ExpenseMapper {

    @Override
    public Expense expenseDtoToExpense(ExpenseDto expenseDto) {
        if ( expenseDto == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setUser( mapUsernameToUser( expenseDto.getUsername() ) );
        expense.setExpenseId( expenseDto.getExpenseId() );
        expense.setName( expenseDto.getName() );
        expense.setAmount( expenseDto.getAmount() );
        expense.setCategory( mapCategoryNameToCategory( expenseDto.getCategory() ) );
        expense.setDate( expenseDto.getDate() );

        return expense;
    }

    @Override
    public ExpenseDto expenseToExpenseDto(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDto.ExpenseDtoBuilder<?, ?> expenseDto = ExpenseDto.builder();

        expenseDto.username( mapUserToUsername( expense.getUser() ) );
        expenseDto.expenseId( expense.getExpenseId() );
        expenseDto.name( expense.getName() );
        expenseDto.amount( expense.getAmount() );
        expenseDto.category( mapExpenseCategoryToString( expense.getCategory() ) );
        expenseDto.date( expense.getDate() );

        return expenseDto.build();
    }
}
