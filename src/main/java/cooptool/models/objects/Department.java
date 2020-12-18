package cooptool.models.objects;

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

    @Override
    public String toString() {
        return abbreviation + " " + year;
    }
}
