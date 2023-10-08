package met.cs673.team1.common;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class MonthYearFormatter {

    private final DateTimeFormatter formatter;

    public MonthYearFormatter(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

   public YearMonth formatMonthYearString(String input) {
       StringBuilder output = new StringBuilder(input);
       output.setCharAt(0, Character.toUpperCase(output.charAt(0)));
       output.setCharAt(1, Character.toLowerCase(output.charAt(1)));
       output.setCharAt(2, Character.toLowerCase(output.charAt(2)));
       return YearMonth.parse(output.toString(), formatter);
   }
}
