package cooptool.views;

public enum ViewPath {

    LOGIN("login.fxml"),
    REGISTER("register.fxml"),
    HOME("");

    private final String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
