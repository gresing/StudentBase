package StudentBase.DAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by gres on 18.06.2016.
 */
public interface GenericDao<T> {

    T create();

    void persist(T object) throws SQLException;

    T read(int key) throws SQLException;

    void update(T object) throws SQLException;

    void delete(T object) throws SQLException;

    List<T> getAll() throws SQLException;
}
