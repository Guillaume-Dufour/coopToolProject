package cooptool.views;

public enum ViewPath {

    LOGIN("login.fxml"),
    REGISTER("register.fxml"),
    STUDENT_HOME("views/student/home_student.fxml"),
    ADMIN_HOME("views/admin/home_admin.fxml"),
    HOME("");

    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
