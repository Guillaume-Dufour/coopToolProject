package cooptool.models.enumDatabase;

/**
 * ValidationUserTable enum
 */
public enum ValidationUserTable implements TableInterface {

    ID_USER("id_user"),
    CODE_VALIDATION("code_validation");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    ValidationUserTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
