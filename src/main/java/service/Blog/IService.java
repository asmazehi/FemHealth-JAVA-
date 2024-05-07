package service.Blog;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
public interface IService <T>{

    public void add(T t) throws SQLException;
    public void update (T t) throws SQLException;
    public void delete (int id) throws SQLException;
    public List<T> select()throws SQLException;
    Map<String, Integer> calculerStatistiquesDateSignature();
    public interface Callback<P,R> {

        public R call(P param);

    }

}