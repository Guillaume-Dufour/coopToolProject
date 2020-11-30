package cooptool.exceptions;

public class MailNotFound extends Exception{
    public MailNotFound (){
        super ("Mail does not exist");
    }
}
