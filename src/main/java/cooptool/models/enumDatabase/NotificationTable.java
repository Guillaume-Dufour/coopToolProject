package cooptool.models.enumDatabase;

public enum NotificationTable implements TableInterface {

    ID_NOTIFICATION("id_notification"),
    ID_USER("id_user"),
    CONTENT_NOTIFICATION("content_notification");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    NotificationTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}