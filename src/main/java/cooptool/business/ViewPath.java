package cooptool.business;

public enum ViewPath {

    LOGIN("views/login.fxml"),
    VALIDATE("views/userManagement/validate_student.fxml"),
    REGISTER("views/userManagement/register.fxml"),
    HOME("views/home.fxml"),
    DELETE_ACCOUNT("views/userManagement/delete_profil.fxml"),
    UPDATE_STUDENT_ACCOUNT("views/userManagement/update_profil_student.fxml"),
    UPDATE_ADMIN_ACCOUNT("views/userManagement/update_profil_admin.fxml"),
    FORGOT_PASSWORD("views/userManagement/new_password.fxml"),
    CREATE_MODIFY_DEPARTMENT("views/departmentManagement/create_modify_department.fxml"),
    CREATE_MODIFY_SUBJECT("views/subjectManagement/create_modify_subject.fxml"),
    HANDLE_DEPARTMENTS("views/handle_departments.fxml"),
    SEARCH_STUDENT("views/userManagement/search_student_by_department.fxml"),
    STUDENT_PROFIL("views/userManagement/student_profil.fxml"),
    MENTORING_DEMAND_HOME_PAGE("views/mentoringDemands/home_page.fxml"),
    CREATE_MENTORING_DEMAND("views/mentoringDemands/creation.fxml"),
    GET_MENTORING_DEMAND("views/mentoringDemands/get_demand.fxml"),
    QUICK_HELP_POST_HOME_PAGE("views/quickHelpPosts/home_page.fxml"),
    CREATE_QUICK_HELP_POST("views/quickHelpPosts/creation.fxml");


    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
