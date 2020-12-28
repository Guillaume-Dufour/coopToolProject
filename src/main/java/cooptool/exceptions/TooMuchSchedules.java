package cooptool.exceptions;

public class TooMuchSchedules extends Exception{
    public TooMuchSchedules() {
        super("The demand can't have more than 10 schedules");
    }
}
