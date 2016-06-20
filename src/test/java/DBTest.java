import StudentBase.DAO.DaoFactory;
import StudentBase.DAO.GenericDao;
import StudentBase.DAO.HSQLDBDAO;
import StudentBase.Entities.Group;
import StudentBase.Entities.Student;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * Created by gres on 18.06.2016.
 */
public class DBTest {
    @Test
    public void getAllGroup() throws Exception {
        DaoFactory df = new HSQLDBDAO();
        List<Group> groupList;
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection);
            groupList = groupDao.getAll();
        }
        Assert.assertNotNull(groupList);
        Assert.assertTrue(groupList.size() > 0);
    }
    @Test
    public void getAllStudents() throws Exception {
        DaoFactory df = new HSQLDBDAO();
        List<Student> studentList;
        try (Connection connection = df.getConnection()) {
            GenericDao<Student> studentDao = df.getStudentDao(connection);
            studentList = studentDao.getAll();
        }
        Assert.assertNotNull(studentList);
        Assert.assertTrue(studentList.size() > 0);
    }
}

