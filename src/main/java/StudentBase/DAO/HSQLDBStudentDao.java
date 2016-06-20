package StudentBase.DAO;

import StudentBase.Entities.Group;
import StudentBase.Entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HSQLDBStudentDao implements GenericDao<Student> {


    private Connection connection = null;

    public HSQLDBStudentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student create() {
        return null;
    }

    @Override
    public void persist(Student student) throws SQLException {
        String sql = "INSERT INTO Student ( name, surname, patronymic, birthday, group_id) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, student.getSurname());
        ps.setString(3, student.getPatronymic());
        ps.setDate(4, new Date(student.getBirthday().getTime()));
        ps.setLong(5, student.getGroupId());

        ps.execute();
    }

    @Override
    public Student read(int key) throws SQLException {
        return null;
    }

    @Override
    public void update(Student student) throws SQLException {
        String sql = "UPDATE Student SET name = ? , surname = ?, patronymic = ?, birthday = ? , group_id = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, student.getSurname());
        ps.setString(3, student.getPatronymic());
        ps.setDate(4, new Date(student.getBirthday().getTime()));
        ps.setLong(5, student.getGroupId());
        ps.setLong(6, student.getId());

        ps.execute();
    }

    @Override
    public void delete(Student student) throws SQLException {
        String sql = "DELETE FROM Student WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, student.getId());
        ps.execute();
    }

    @Override
    public List<Student> getAll() throws SQLException {
        String sql = "SELECT * FROM Student;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Student> studentList = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setBirthday(rs.getDate("birthday"));
            student.setName(rs.getString("name"));
            student.setSurname(rs.getString("surname"));
            student.setPatronymic(rs.getString("patronymic"));
            student.setGroupId(rs.getLong("group_id"));
            studentList.add(student);
        }
        return studentList;
    }
}
