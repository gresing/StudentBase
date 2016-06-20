import StudentBase.DAO.DaoFactory;
import StudentBase.DAO.GenericDao;
import StudentBase.DAO.HSQLDBDAO;
import StudentBase.Entities.Group;
import StudentBase.Entities.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Created by gres on 18.06.2016.
 */
public class DBTest {
    Student s;
    Group g;
    DaoFactory df;

    @Before
    public void init() {
        g = new Group();
        g.setNumber(1);
        g.setFacultyName("test");
        g.setId((long) 9999999);

        s = new Student();
        s.setName("test");
        s.setSurname("test");
        s.setPatronymic("test");
        Date d = new Date();
        s.setBirthday(d);
        s.setGroupId((long) 1);
        s.setId((long) 9999999);

        df = new HSQLDBDAO();
    }

    @Test
    public void persistGroupTest() throws Exception {
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection);
            groupDao.persist(g);
        }
    }

    @Test
    public void persistStudentTest() throws Exception {
        try (Connection connection = df.getConnection()) {
            GenericDao<Student> studentDao = df.getStudentDao(connection);
            studentDao.persist(s);
        }
    }

    @Test
    public void deleteStudentTest() throws Exception {
        try (Connection connection = df.getConnection()) {
            GenericDao<Student> studentDao = df.getStudentDao(connection);
            studentDao.delete(s);
        }
    }

    @Test
    public void deleteGroupTest() throws Exception {
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection);
            groupDao.delete(g);
        }
    }


    @Test
    public void getAllGroupTest() throws Exception {
        List<Group> groupList;
        try (Connection connection = df.getConnection()) {
            GenericDao<Group> groupDao = df.getGroupDao(connection);
            groupList = groupDao.getAll();
        }
        Assert.assertNotNull(groupList);
        Assert.assertTrue(groupList.size() > 0);
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        List<Student> studentList;
        try (Connection connection = df.getConnection()) {
            GenericDao<Student> studentDao = df.getStudentDao(connection);
            studentList = studentDao.getAll();
        }
        Assert.assertNotNull(studentList);
        Assert.assertTrue(studentList.size() > 0);
    }


}

