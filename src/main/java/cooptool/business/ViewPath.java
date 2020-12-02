package cooptool.business;

public enum ViewPath {

    LOGIN("views/login.fxml"),
    REGISTER("views/register.fxml"),
    STUDENT_HOME("views/student/home_student.fxml"),
    ADMIN_HOME("views/admin/home_admin.fxml");

    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
