package cooptool.utils;

import java.util.ArrayList;

public class TimeUtils {

    private static ArrayList<Integer> hours;
    private static ArrayList<Integer> minutes;

    public static ArrayList<Integer> getHoursArrayList(){
        if(hours == null){
            hours = new ArrayList<>();
            for(int i=0;i<24;i++){
                hours.add(i);
            }
        }
        return hours;
    }

    public static ArrayList<Integer> getMinutesArrayList(){
        if(minutes == null){
            minutes = new ArrayList<>();
            for(int i=0;i<24;i++){
                minutes.add(i);
            }
        }
        return minutes;
    }
}
