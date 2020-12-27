package cooptool.models.enumDatabase;

public enum ScheduleTable {

    ID_POST("id_post"),
    DATE_POST_SESSION("date_post_session"),
    CREATOR_ID("creator_id");

    private final String path;

    ScheduleTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
