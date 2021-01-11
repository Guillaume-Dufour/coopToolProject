package cooptool.models.enumDatabase;

/**
 * CommentTable enum
 */
public enum CommentTable implements TableInterface {

    ID_COMMENT("id_comment"),
    CONTENT_COMMENT("content_comment"),
    DATE_COMMENT("date_comment"),
    ID_USER_CREATOR("id_user_creator"),
    ID_POST("id_post");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    CommentTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
