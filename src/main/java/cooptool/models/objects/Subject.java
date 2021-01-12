package cooptool.models.objects;

import java.util.Objects;

/**
 * Subject class
 */
public class Subject {

    /**
     * ID of the subject
     */
    private int id;

    /**
     * Name of the subject
     */
    private String name;

    /**
     * Subject availability
     */
    private int available;

    /**
     * Department of the subject
     */
    private Department department;

    /**
     * Constructor
     * @param id Subject ID
     * @param name Subject name
     * @param available Subject availability
     * @param department Department of the subject
     */
    public Subject(int id, String name, int available, Department department) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.department = department;
    }

    /**
     * Constructor
     * @param name Subject name
     * @param department Department of the subject
     */
    public Subject(String name, Department department) {
        this(0, name, 1, department);
    }

    /**
     * Get the subject ID
     * @return Subject ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the subject name
     * @return Subject name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the subject availability
     * @return Subject availability
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Get the department of the subject
     * @return Department of the subject
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Set the new name of the subject
     * @param name New name of the subject
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the new department of the subject
     * @param department New department of the subject
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Change the availability of the subject
     * @return New state of availability
     */
    public int changeAvailability() {
        if (available == 0) {
            available = 1;
        }
        else {
            available = 0;
        }

        return available;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", available=" + available +
                ", department=" + department +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
