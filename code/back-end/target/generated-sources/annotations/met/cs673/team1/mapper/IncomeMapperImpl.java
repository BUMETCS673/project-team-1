package met.cs673.team1.mapper;

import javax.annotation.processing.Generated;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-25T07:04:34-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class IncomeMapperImpl extends IncomeMapper {

    @Override
    public Income incomeDtoToIncome(IncomeDto incomeDto) {
        if ( incomeDto == null ) {
            return null;
        }

        Income income = new Income();

        income.setUser( mapUsernameToUser( incomeDto.getUsername() ) );
        income.setName( incomeDto.getName() );
        income.setAmount( incomeDto.getAmount() );
        income.setDate( incomeDto.getDate() );

        return income;
    }

    @Override
    public IncomeDto incomeToIncomeDto(Income income) {
        if ( income == null ) {
            return null;
        }

        IncomeDto incomeDto = new IncomeDto();

        incomeDto.setUsername( mapUserToUsername( income.getUser() ) );
        incomeDto.setName( income.getName() );
        incomeDto.setAmount( income.getAmount() );
        incomeDto.setDate( income.getDate() );

        return incomeDto;
    }
}
