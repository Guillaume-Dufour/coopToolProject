package cooptool.models.objects;

public class Subject {

    /**
     * ID of the subject
     */
    private int id;

    /**
     * Name of the subject
     */
    private String name;

    private int available;

    /**
     * Department of the subject
     */
    private Department department;

    public Subject(int id, String name, int available, Department department) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailable() {
        return available;
    }

    public Department getDepartment() {
        return department;
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
}
