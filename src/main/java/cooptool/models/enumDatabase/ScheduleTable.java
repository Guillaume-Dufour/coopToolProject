package cooptool.models.enumDatabase;

public enum ScheduleTable implements TableInterface {

    ID_POST("id_post"),
    DATE_POST_SESSION("date_post_session"),
    ID_CREATOR("creator_id");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    ScheduleTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
