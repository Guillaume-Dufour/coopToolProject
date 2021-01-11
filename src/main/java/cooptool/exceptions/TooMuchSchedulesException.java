package cooptool.exceptions;

public class TooMuchSchedulesException extends Exception{
    public TooMuchSchedulesException() {
        super("The demand can't have more than 10 schedules");
    }
}
