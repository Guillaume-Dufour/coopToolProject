package cooptool.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TimeUtils class
 */
public class TimeUtils {

    private static List<Integer> hours;
    private static List<Integer> minutes;

    /**
     * Get the list of hours in a day
     * @return List of hours
     */
    public static List<Integer> getHoursArrayList() {
        if (hours == null) {
            hours = new ArrayList<>();
            for(int i = 0; i < 24; i++) {
                hours.add(i);
            }
        }
        return hours;
    }

    /**
     * Get the list of minutes in a minute
     * @return List of minutes
     */
    public static List<Integer> getMinutesArrayList() {
        if (minutes == null) {
            minutes = new ArrayList<>();
            for(int i = 0; i < 60; i++) {
                minutes.add(i);
            }
        }
        return minutes;
    }

    /**
     * Format the number in parameter
     * @param value Value we want to format
     * @return Number printed with minimum 2 digits
     */
    public static String format(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }


    /**
     * Parse the date in parameter in LocalDateTime date
     * @param formattedDate Date to parse
     * @return Date with LocalDateTime type
     */
    public static LocalDateTime parse(String formattedDate) {
        return LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
