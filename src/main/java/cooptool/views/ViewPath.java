package cooptool.views;

public enum ViewPath {
    LOGIN("login.fxml");
    private String path;

    ViewPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
