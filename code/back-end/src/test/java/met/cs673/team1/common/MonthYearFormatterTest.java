package met.cs673.team1.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MonthYearFormatterTest {

    static final String EXPECTED = "Jun2023";
    static final List<String> ARGS = Arrays.asList("jun2023", "JUN2023", "jUN2023");

    @Mock
    DateTimeFormatter formatter;

    @InjectMocks
    MonthYearFormatter ymFormatter;

    @Test
    void testFormatMonthYearString() {
        YearMonth mockedYearMonth = YearMonth.of(2023, 6);
        for (String arg : ARGS) {
            try (MockedStatic<YearMonth> ymStaticMock = mockStatic(YearMonth.class)) {
                ymStaticMock.when(() -> YearMonth.parse(anyString(), any(DateTimeFormatter.class))).thenReturn(mockedYearMonth);
                ymFormatter.formatMonthYearString(arg);
                ymStaticMock.verify(() -> YearMonth.parse(EXPECTED, formatter));
            }
        }
    }

    @Test
    void testFormatMonthYearThrowsException() {
        try (MockedStatic<YearMonth> ymStaticMock = mockStatic(YearMonth.class)) {
            ymStaticMock.when(() -> YearMonth.parse(anyString(), any(DateTimeFormatter.class)))
                    .thenThrow(DateTimeParseException.class);
            ymFormatter.formatMonthYearString("ju2023");
        } catch (DateTimeParseException e) {

        }
    }
}
