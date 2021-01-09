package cooptool.models.objects;

import java.util.Objects;

public class Department {

    /**
     * ID of the department
     */
    private int id;

    /**
     * Name of the department
     */
    private String speciality;

    /**
     * Year
     */
    private int year;

    /**
     * Abbreviation of the department (speciality + year)
     */
    private String abbreviation;

    private int available;

    public Department(int id, String speciality, int year, String abbreviation, int available) {
        this.id = id;
        this.speciality = speciality;
        this.year = year;
        this.abbreviation = abbreviation;
        this.available = available;
    }

    public Department(String speciality, int year, String abbreviation) {
        this(0, speciality, year, abbreviation, 1);
    }

    public int getId() {
        return id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getYear() {
        return year;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getAvailable() {
        return available;
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

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setYear(int year) {
        this.year = year;
    }

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
