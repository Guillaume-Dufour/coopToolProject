package cooptool.models.enumDatabase;

public enum BrowsingHistoryTable {

    ID_USER("id_user"),
    ID_POST("id_post");

    private final String path;

    BrowsingHistoryTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
