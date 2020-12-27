package cooptool.models.enumDatabase;

public enum PostTable {

    ID_POST("id_post"),
    DESCRIPTION_POST("description_post"),
    DATE_POST("date_post"),
    TYPE_POST("type_post"),
    ID_USER_CREATOR("id_user_creator"),
    ID_SUBJECT("id_subject");

    private final String path;

    PostTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
