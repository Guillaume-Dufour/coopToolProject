package cooptool.models.enumDatabase;

public enum MessageTable {

    ID_USER_SENDER("id_user_sender"),
    ID_USER_RECEIVER("id_user_receiver"),
    DATE_MESSAGE("date_message"),
    CONTENT_MESSAGE("content_message");

    private final String path;

    MessageTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
