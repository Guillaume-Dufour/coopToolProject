package cooptool.models.objects;

public class StudentRole extends UserRole {

    /**
     * First name of the student
     */
    private String firstName;

    /**
     * Name of the student
     */
    private String lastName;

    /**
     * Description of the student
     */
    private String description;

    /**
     * Department of the student
     */
    private Department department;

    public StudentRole(String firstName, String lastName, String description, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
