package cooptool.models.enumDatabase;

/**
 * ScheduleTable enum
 */
public enum ScheduleTable implements TableInterface {

    ID_POST("id_post"),
    DATE_POST_SESSION("date_post_session"),
    ID_CREATOR("id_creator");

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
