package cooptool.business;

public enum ViewPath {

    LOGIN("views/login.fxml"),
    REGISTER("views/register.fxml"),
    HOME("views/home.fxml"),
    PROFIL("views/student/profil.fxml"),
    DELETE_ACCOUNT("views/student/delete_profil.fxml"),
    UPDATE_ACCOUNT("views/student/update_profil.fxml");


    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
