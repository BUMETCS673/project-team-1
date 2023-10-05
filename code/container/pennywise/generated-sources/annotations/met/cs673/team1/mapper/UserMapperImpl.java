package met.cs673.team1.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.Expense;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.domain.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-01T14:38:21-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User userPostDtoToUser(UserPostDto userPostDto) {
        if ( userPostDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userPostDto.getUsername() );
        user.setPassword( userPostDto.getPassword() );
        user.setEmail( userPostDto.getEmail() );
        user.setFirstName( userPostDto.getFirstName() );
        user.setLastName( userPostDto.getLastName() );
        user.setRoles( getRolesFromList( userPostDto.getRoles() ) );

        return user;
    }

    @Override
    public UserGetDto userToUserGetDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserGetDto userGetDto = new UserGetDto();

        userGetDto.setUsername( user.getUsername() );
        userGetDto.setEmail( user.getEmail() );
        userGetDto.setFirstName( user.getFirstName() );
        userGetDto.setLastName( user.getLastName() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userGetDto.setRoles( new ArrayList<Role>( set ) );
        }
        List<Expense> list = user.getExpenses();
        if ( list != null ) {
            userGetDto.setExpenses( new ArrayList<Expense>( list ) );
        }

        return userGetDto;
    }
}
