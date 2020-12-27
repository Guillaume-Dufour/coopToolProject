package cooptool.models.enumDatabase;

public enum DepartmentTable {

    ID_DEPARTMENT("id_department"),
    NAME_DEPARTMENT("name_department"),
    ABBREVIATION_DEPARTMENT("abbreviation_department"),
    YEAR_DEPARTMENT("year_department"),
    AVAILABLE("available");

    private final String path;

    DepartmentTable(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
