package met.cs673.team1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.*;
import met.cs673.team1.domain.dto.UserGetDto;
import met.cs673.team1.repository.RoleRepository;
import met.cs673.team1.domain.entity.Role;
import met.cs673.team1.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    private static String USERNAME = "username";
    private static String USER_ROLE = "ROLE_USER";
    private static String ADMIN_ROLE = "ROLE_ADMIN";

    @Mock
    private RoleRepository roleRepository;

    private UserMapper userMapper;
    private Role role1;
    private Role role2;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
        ReflectionTestUtils.setField(userMapper, "roleRepository", roleRepository);

        role1 = new Role();
        role1.setName(USER_ROLE);
        role2 = new Role();
        role2.setName(ADMIN_ROLE);
    }

    @Test
    void testMapperInstantiated() {
        User user = new User();
        user.setUsername(USERNAME);
        UserGetDto dto = userMapper.userToUserGetDto(user);
        assertThat(dto.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void testListToSetRoleMapping() {
        List<String> roleList = Arrays.asList(USER_ROLE, ADMIN_ROLE);
        doReturn(Optional.of(role1), Optional.of(role2)).when(roleRepository).findByName(anyString());

        Set<Role> roles = userMapper.getRolesFromList(roleList);

        verify(roleRepository).findByName(USER_ROLE);
        verify(roleRepository).findByName(ADMIN_ROLE);
        assertEquals(2, roles.size());
        assertThat(roles).contains(role1, role2);
    }

    @Test
    void testListToSetRoleMapping_OptionalEmpty() {
        List<String> roleList = Arrays.asList(USER_ROLE, ADMIN_ROLE);
        doReturn(Optional.of(role1), Optional.empty()).when(roleRepository).findByName(anyString());

        Set<Role> roles = userMapper.getRolesFromList(roleList);

        verify(roleRepository, times(2)).findByName(anyString());
        assertEquals(1, roles.size());
        assertThat(roles).contains(role1);
    }

    @Test
    void testEmptyRoleList() {
        Set<Role> roles = userMapper.getRolesFromList(new ArrayList<>());
        verify(roleRepository, times(0)).findByName(anyString());
    }
}
