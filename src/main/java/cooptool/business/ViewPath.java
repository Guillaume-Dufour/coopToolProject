package cooptool.business;

public enum ViewPath {

    LOGIN("views/login.fxml"),
    VALIDATE("views/userManagement/validate_student.fxml"),
    REGISTER("views/userManagement/register.fxml"),
    HOME("views/home.fxml"),
    STUDENT_PROFIL("views/userManagement/profil_student.fxml"),
    DELETE_ACCOUNT("views/userManagement/delete_profil.fxml"),
    UPDATE_STUDENT_ACCOUNT("views/userManagement/update_profil_student.fxml"),
    UPDATE_ADMIN_ACCOUNT("views/userManagement/update_profil_admin.fxml"),
    FORGOT_PASSWORD("views/userManagement/new_password.fxml"),
    CREATE_MODIFY_DEPARTMENT("views/departmentManagement/create_modify_department.fxml"),
    HANDLE_DEPARTMENTS("views/handle_departments.fxml"),
    UPDATE_SUBJECT("views/subjectManagement/update.fxml"),
    UPDATE_DEPARTMENT("views/departmentManagement/update.fxml"),
    SEARCH_STUDENT("views/userManagement/searchOtherStudent/search_student_by_department.fxml"),
    STUDENT_ACCOUNT("views/userManagement/searchOtherStudent/other_profil.fxml");


    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
