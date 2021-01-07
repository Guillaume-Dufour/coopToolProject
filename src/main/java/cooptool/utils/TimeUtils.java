package cooptool.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeUtils {

    private static List<Integer> hours;
    private static List<Integer> minutes;

    public static List<Integer> getHoursArrayList() {
        if (hours == null) {
            hours = new ArrayList<>();
            for(int i = 0; i < 24; i++) {
                hours.add(i);
            }
        }
        return hours;
    }

    public static List<Integer> getMinutesArrayList() {
        if (minutes == null) {
            minutes = new ArrayList<>();
            for(int i = 0; i < 60; i++) {
                minutes.add(i);
            }
        }
        return minutes;
    }

    public static String format(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }


    public static LocalDateTime parse(String formattedDate) {
        return LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }



}
