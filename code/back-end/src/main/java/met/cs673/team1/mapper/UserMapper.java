package met.cs673.team1.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.domain.dto.UserPostDto;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.domain.entity.User;
import met.cs673.team1.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Map between ExpenseDto and Expense objects on persistence to and retrieval from the database.
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    public abstract User userPostDtoToUser(UserPostDto userPostDto);

    public Set<Role> getRolesFromList(List<String> roles) {
        Optional<Role> optRole;
        Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            optRole = roleRepository.findByName(roleName);
            if (optRole.isPresent()) {
                roleSet.add(optRole.get());
            }
        }

        return roleSet;
    }

    public abstract UserGetDto userToUserGetDto(User user);
}
