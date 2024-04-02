package service.Blog;

import utils.MyDataBase;

import java.sql.Connection;
import models.Blog.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PublicationService {
    Connection connection;
    public PublicationService() {
        connection = MyDataBase.getInstance().getConnection();
    }
    public void add ( Publication publication)throws SQLException {
        String sql = "insert into Publication (titre,datepub,contenu ,image) values (? , CURRENT_TIMESTAMP , ? , ? )";;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, publication.getTitre());
        statement.setString(2, publication.getContenu());
        statement.setString(3, publication.getImage());
        statement.executeUpdate();
    }
    public void update(Publication publication) throws SQLException {
        String sql = "UPDATE Publication SET titre=?, contenu=?, image=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, publication.getTitre());
        statement.setString(2, publication.getContenu());
        statement.setString(3, publication.getImage());
        statement.setInt(4, publication.getId());
        statement.executeUpdate();
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    public List<Publication> select() throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT * FROM Publication";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            // Set other attributes of Publication as needed
            publications.add(publication);
        }
        return publications;
    }
    public Publication getById(int id) {
        return null;
    }
}
