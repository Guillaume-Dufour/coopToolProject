package cooptool.models.enumDatabase;

public enum SubjectTable {

    ID_SUBJECT("id_subject"),
    NAME_SUBJECT("name_subject"),
    AVAILABLE("available"),
    ID_DEPARTMENT("id_department");

    private final String path;

    SubjectTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
