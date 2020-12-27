package cooptool.models.enumDatabase;

public enum SubjectTable implements PapaInterface {

    ID_SUBJECT("id_subject"),
    NAME_SUBJECT("name_subject"),
    AVAILABLE("available"),
    ID_DEPARTMENT("id_department");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    SubjectTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
