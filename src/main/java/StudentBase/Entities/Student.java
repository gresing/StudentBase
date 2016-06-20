package StudentBase.Entities;


import java.util.Date;

/**
 * Created by gres on 18.06.2016.
 */
public class Student {
    private Long id = null;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthday;
    private Long groupId;

    public Student() {
    }

    public Student(Long id, String name, String surname, String patronymic, Date birthday, Long groupId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.groupId = groupId;
    }

    public Student(String name, String surname, String patronymic, Date birthday, Long groupId) {

        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.groupId = groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
