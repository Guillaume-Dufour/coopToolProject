package cooptool.models.objects;

import java.util.Objects;

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

    public void setAvailable(int available) {
        this.available = available;
    }

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
