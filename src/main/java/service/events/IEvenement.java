package service;

import java.sql.SQLException;
import java.util.List;

public interface IEvenement<T> {
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> select() throws SQLException;
}
