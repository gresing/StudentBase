package StudentBase.DAO;

import StudentBase.Entities.Group;
import StudentBase.Entities.Student;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gres on 18.06.2016.
 */
public class HSQLDBDAO implements DaoFactory {

    public static boolean firstRun = true;

    private final String path = "mypath/";
    private final String dbname = "mydb";
    private final String connectionString = "jdbc:hsqldb:mem:" + path + dbname;
    private final String login = "root";
    private final String password = "password";

    Connection connection;

    @Override
    public Connection getConnection() throws SQLException { // TODO: закрывать соединение через "SHUTDOWN"?
        connection = DriverManager.getConnection(connectionString, login, password);
        if (firstRun) {
            try {
                createAndFillTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            firstRun = false;
        }
        return connection;
    }

    @Override
    public GenericDao<Group> getGroupDao(Connection connection) {
        return new HSQLDBGroupDao(connection);
    }

    @Override
    public GenericDao<Student> getStudentDao(Connection connection) {
        return new HSQLDBStudentDao(connection);
    }

    private void createAndFillTable() throws SQLException, IOException {

        String createGroupTableSQLFilePath = "./sql/createtablegroup.sql";
        String createStudentTableSQLFilePath = "./sql/createtablestudent.sql";
        String fillTableSQLFilePath = "./sql/filltable.sql";

        List<String> filesPaths = new ArrayList<>();
        filesPaths.add(createGroupTableSQLFilePath);
        filesPaths.add(createStudentTableSQLFilePath);
        filesPaths.add(fillTableSQLFilePath);
        Reader reader;
        Statement statement;
        for (String f : filesPaths) {
            reader = new BufferedReader(new FileReader(f));
            int v;
            String request = "";
            while ((v = reader.read()) != -1) {
                request += (char) v;
            }
            statement = connection.createStatement();
            statement.execute(request);
        }

    }




}
