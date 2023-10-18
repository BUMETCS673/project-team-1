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

    /**
     * This method takes a string representing a month and year as input, and returns a YearMonth object.
     * @param input String with month abbreviation and year (e.g. jan2023)
     * @return YearMonth object
     */
   public YearMonth formatMonthYearString(String input) {
       StringBuilder output = new StringBuilder(input);
       output.setCharAt(0, Character.toUpperCase(output.charAt(0)));
       output.setCharAt(1, Character.toLowerCase(output.charAt(1)));
       output.setCharAt(2, Character.toLowerCase(output.charAt(2)));
       return YearMonth.parse(output.toString(), formatter);
   }
}
