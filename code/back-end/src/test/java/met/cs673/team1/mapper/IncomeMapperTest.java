package met.cs673.team1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import java.sql.Date;
import met.cs673.team1.domain.dto.IncomeDto;
import met.cs673.team1.domain.entity.Income;
import met.cs673.team1.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class IncomeMapperTest {

    private static String USERNAME = "username";
    private static Date DATE = new Date(2023, 9, 12);
    private static Double AMOUNT = 24.5;
    private static String NAME = "name";

    @Mock
    MapperUtil mapperUtil;

    IncomeMapper incomeMapper;

    @BeforeEach
    void setUp() {
        incomeMapper = Mappers.getMapper(IncomeMapper.class);
        ReflectionTestUtils.setField(incomeMapper, "mapperUtil", mapperUtil);
    }

    @Test
    void testNullIncomeToIncomeDto() {
        IncomeDto dto = incomeMapper.incomeToIncomeDto(null);
        assertNull(dto);
    }

    @Test
    void testIncomeToIncomeDto() {
        User u = new User();
        u.setUsername(USERNAME);

        Income inc = new Income();
        inc.setDate(DATE);
        inc.setUser(u);
        inc.setAmount(AMOUNT);
        inc.setName(NAME);

        IncomeDto dto = incomeMapper.incomeToIncomeDto(inc);

        assertThat(dto.getDate().compareTo(DATE)).isZero();
        assertThat(dto.getUsername()).isEqualTo(USERNAME);
        assertThat(dto.getAmount()).isEqualTo(AMOUNT);
        assertThat(dto.getName()).isEqualTo(NAME);
    }

    @Test
    void testNullIncomeDtoToIncome() {
        Income inc = incomeMapper.incomeDtoToIncome(null);
        assertNull(inc);
    }

    @Test
    void testIncomeDtoToIncome() {
        User u = new User();
        u.setUsername(USERNAME);
        doReturn(u).when(mapperUtil).mapUsernameToUser(anyString());

        IncomeDto dto = IncomeDto.builder()
                .name(NAME)
                .date(DATE)
                .amount(AMOUNT)
                .username(USERNAME)
                .build();

        Income inc = incomeMapper.incomeDtoToIncome(dto);

        assertThat(inc.getName()).isEqualTo(NAME);
        assertThat(inc.getDate().compareTo(DATE)).isZero();
        assertThat(inc.getAmount()).isEqualTo(AMOUNT);
        assertNotNull(inc.getUser());
        assertThat(inc.getUser().getUsername()).isEqualTo(USERNAME);
    }
}
