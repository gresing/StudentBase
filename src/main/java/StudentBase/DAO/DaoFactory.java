package StudentBase.DAO;

import StudentBase.Entities.Group;
import StudentBase.Entities.Student;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gres on 18.06.2016.
 */
public interface DaoFactory {
    Connection getConnection() throws SQLException;
    GenericDao<Group> getGroupDao(Connection connection);
    GenericDao<Student> getStudentDao(Connection connection);
}
