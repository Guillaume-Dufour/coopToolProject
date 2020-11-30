package cooptool.exceptions;

public class UnmatchedPassword extends Exception {
    public UnmatchedPassword() {
        super("Password does not match");
    }
}
