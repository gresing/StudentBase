package StudentBase.DAO;

import StudentBase.Entities.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gres on 18.06.2016.
 */
public class HSQLDBGroupDao implements GenericDao<Group> {

    private Connection connection = null;

    public HSQLDBGroupDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Group create() {
        return new Group();
    }

    @Override
    public void persist(Group gr) throws SQLException {
        String sql = "INSERT INTO StudentGroup ( number, facultyname) VALUES (?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, gr.getNumber());
        ps.setString(2, gr.getFacultyName());
        ps.execute();
    }


    @Override
    public Group read(int key) throws SQLException {
        String sql = "SELECT * FROM StudentGroup WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Group group = new Group();
        group.setId(rs.getLong("id"));
        group.setNumber(rs.getInt("number"));
        group.setFacultyName(rs.getString("department"));
        return group;
    }

    @Override
    public void update(Group group) throws SQLException {
        String sql = "UPDATE StudentGroup SET number = ? , facultyname = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, group.getNumber());
        ps.setString(2, group.getFacultyName());
        ps.setLong(3, group.getId());
        ps.execute();
    }

    @Override
    public void delete(Group group) throws SQLException {
        String sql = "DELETE FROM StudentGroup WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, group.getId());
        ps.execute();
    }

    @Override
    public List<Group> getAll() throws SQLException {
        String sql = "SELECT * FROM StudentGroup;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Group> groupList = new ArrayList<>();
        while (rs.next()) {
            Group group = new Group();
            group.setId(rs.getLong("id"));
            group.setNumber(rs.getInt("number"));
            group.setFacultyName(rs.getString("facultyName"));
            groupList.add(group);
        }
        return groupList;
    }
}
