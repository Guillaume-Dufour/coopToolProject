package cooptool.models.enumDatabase;

public enum ValidationUserTable {

    ID_USER("id_user"),
    CODE_VALIDATION("code_validation");

    private final String path;

    ValidationUserTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
