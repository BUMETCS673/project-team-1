package met.cs673.team1.mapper;

import java.sql.Date;
import javax.annotation.processing.Generated;
import met.cs673.team1.domain.dto.ExpenseDto;
import met.cs673.team1.domain.entity.Expense;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-01T14:38:21-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class ExpenseMapperImpl extends ExpenseMapper {

    @Override
    public Expense expenseDtoToExpense(ExpenseDto expenseDto) {
        if ( expenseDto == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setExpenseId( expenseDto.getExpenseId() );
        expense.setName( expenseDto.getName() );
        expense.setAmount( expenseDto.getAmount() );
        expense.setCategory( mapCategoryNameToCategory( expenseDto.getCategory() ) );
        if ( expenseDto.getDate() != null ) {
            expense.setDate( new Date( expenseDto.getDate().getTime() ) );
        }

        return expense;
    }

    @Override
    public ExpenseDto expenseToExpenseDto(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDto expenseDto = new ExpenseDto();

        expenseDto.setUsername( mapUserToUsername( expense.getUser() ) );
        expenseDto.setExpenseId( expense.getExpenseId() );
        expenseDto.setName( expense.getName() );
        expenseDto.setAmount( expense.getAmount() );
        expenseDto.setCategory( mapExpenseCategoryToString( expense.getCategory() ) );
        expenseDto.setDate( expense.getDate() );

        return expenseDto;
    }
}
