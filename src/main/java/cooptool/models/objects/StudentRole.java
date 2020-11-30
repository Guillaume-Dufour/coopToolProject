package cooptool.models.objects;

public class StudentRole extends UserRole {

    /**
     * First name of the student
     */
    private String firstName;

    /**
     * Name of the student
     */
    private String name;

    /**
     * Description of the student
     */
    private String description;

    /**
     * Department of the student
     */
    private Department department;

    public StudentRole() {
        // TO DO
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartment() {
        return department;
    }
}
