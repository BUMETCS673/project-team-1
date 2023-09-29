package met.cs673.team1.mapper;

import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Map between IncomeDto and Income objects before persisting to and after retrieving from the database.
 */
@Mapper(componentModel = "spring")
public abstract class IncomeMapper {

    @Autowired
    private MapperUtil mapperUtil;

    @Mapping(source = "username", target = "user")
    public abstract Income incomeDtoToIncome(IncomeDto incomeDto);

    public User mapUsernameToUser(String username) {
        return mapperUtil.mapUsernameToUser(username);
    }

    @Mapping(source = "user", target = "username")
    public abstract IncomeDto incomeToIncomeDto(Income income);

    public String mapUserToUsername(User user) {
        return user.getUsername();
    }
}
