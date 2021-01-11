package cooptool.models.objects;

import java.util.Objects;

public class Department {

    /**
     * Department ID
     */
    private int id;

    /**
     * Department name
     */
    private String speciality;

    /**
     * Department year
     */
    private int year;

    /**
     * Department abbreviation (speciality + year)
     */
    private String abbreviation;

    /**
     * Department availability
     */
    private int available;

    /**
     * Constructor
     * @param id Department ID
     * @param speciality Department name
     * @param year Department year
     * @param abbreviation Department abbreviation
     * @param available Department availability
     */
    public Department(int id, String speciality, int year, String abbreviation, int available) {
        this.id = id;
        this.speciality = speciality;
        this.year = year;
        this.abbreviation = abbreviation;
        this.available = available;
    }

    /**
     * Constructor
     * @param speciality Department name
     * @param year Department year
     * @param abbreviation Department abbreviation
     */
    public Department(String speciality, int year, String abbreviation) {
        this(0, speciality, year, abbreviation, 1);
    }

    /**
     * Get the department ID
     * @return Department ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the department name
     * @return Department name
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Get the department year
     * @return Department year
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the department abbreviation
     * @return Department abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Get the deparment availability
     * @return Department availability
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Change the department availability
     * @return New department availability state
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

    /**
     * Set the new department name
     * @param speciality New department name
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /**
     * Set the new department year
     * @param year New year department
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Set the new department abbreviation
     * @param abbreviation New department abbreviation
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return year == that.year && Objects.equals(speciality, that.speciality) && Objects.equals(abbreviation, that.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speciality, year, abbreviation);
    }
}
