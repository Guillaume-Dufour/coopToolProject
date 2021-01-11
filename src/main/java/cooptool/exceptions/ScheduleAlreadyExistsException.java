package cooptool.exceptions;

public class ScheduleAlreadyExistsException extends Exception {
    public ScheduleAlreadyExistsException(){
        super("This schedule already exists");
    }
}
