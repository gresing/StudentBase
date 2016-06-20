package StudentBase.Entities;

/**
 * Created by gres on 18.06.2016.
 */
public class Group {
    private Long id = null;
    private int number;
    private String facultyName;

    public Group() {
    }

    public Group(int number, String facultyName)
    {
        this.number = number;

        this.facultyName = facultyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
