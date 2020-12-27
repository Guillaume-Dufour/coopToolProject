package cooptool.models.enumDatabase;

public enum ParticipationTable {

    ID_USER("id_user"),
    ID_POST("id_post"),
    DATE_POST_SESSION("date_post_session"),
    ROLE_USER("role_user");

    private final String path;

    ParticipationTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
