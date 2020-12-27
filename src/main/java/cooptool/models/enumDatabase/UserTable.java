package cooptool.models.enumDatabase;

public enum UserTable implements PapaInterface{

    ID_USER("id_user"),
    LAST_NAME_USER("last_name_user"),
    FIRST_NAME_USER("first_name_user"),
    MAIL_USER("mail_user"),
    DESCRIPTION_USER("description_user"),
    PASSWORD_USER("password_user"),
    TYPE_USER("type_user"),
    ID_DEPARTMENT("id_department"),
    VALIDATE("validate");

    @Override
    public String toString() {
        return path;
    }

    private final String path;

    UserTable (String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

}
