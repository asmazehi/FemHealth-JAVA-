package service.Blog;

import model.Blog.Publication;
import utils.MyDataBase;

import java.sql.Connection;
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
        System.out.println(publication.getId());
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/femHealth","root", "");
        String query = "SELECT * FROM publication";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            publication.setDatepub(resultSet.getDate("datepub"));
            publications.add(publication);

        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return publications;}
    public Publication getById(int id) {
        return null;
    }

    public Publication getPublicationById(int id_pub) throws SQLException {
        String req = "SELECT * FROM publication WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id_pub);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Publication publication = new Publication();
            publication.setId(rs.getInt("id"));
            publication.setContenu(rs.getString("contenu"));
            publication.setTitre(rs.getString("titre"));
            publication.setImage(rs.getString("image"));
            publication.setDatepub(rs.getTimestamp("datepub"));
            return publication;
        } else {
            // Handle the case where no publication with the given ID was found
            return null;
        }
    }
    public List<Publication> fetchPublicationByTitreAndContenu(String titre, String contenu) throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String query = "SELECT * FROM publication WHERE titre LIKE ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + titre + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            publication.setDatepub(resultSet.getDate("datepub"));
            publications.add(publication);
        }
        return publications;
    }
}
