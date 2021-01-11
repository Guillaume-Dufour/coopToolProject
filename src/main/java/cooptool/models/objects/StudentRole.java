package cooptool.models.objects;

/**
 * StudentRole class
 */
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

    /**
     * Constructor
     * @param firstName Student first name
     * @param lastName Student last name
     * @param description Student description
     * @param department Student department
     */
    public StudentRole(String firstName, String lastName, String description, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.department = department;
    }

    /**
     * Get the student first name
     * @return Student first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the student last name
     * @return Student last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the student description
     * @return Student description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the new student department
     * @param department New student department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Set the new student description
     * @param description New student description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the student department
     * @return Student department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Representation of a student in String format
     * @return Representation of a student
     */
    public String getStudentRepresentation(){
        return String.format(
                "%s %s %s%d",
                firstName,
                lastName,
                department.getAbbreviation(),
                department.getYear()
        );
    }
}
