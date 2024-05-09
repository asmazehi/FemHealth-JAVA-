package service.Blog;

import model.Blog.Publication;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.*;
import java.time.LocalDate;
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
        String query = "SELECT * FROM publication order by  (select count(publication_id ) from commentaire WHERE publication.id=commentaire.publication_id) DESC ";


        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("titre"));
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
    public List<Object[]> findPublicationWithCommentCount() throws SQLException {
        String sql = "SELECT p.id AS publicationId, COUNT(c.id) AS commentCount " +
                "FROM Publication p " +
                "LEFT JOIN Commentaire c ON p.id = c.publication_id " +
                "GROUP BY p.id";


        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<Object[]> result = new ArrayList<>();
        while (resultSet.next()) {
            int publicationId = resultSet.getInt("publicationId");
            int commentCount = resultSet.getInt("commentCount");
            result.add(new Object[]{publicationId, commentCount});
        }

        return result;
    }
    public List<Publication> getPublicationsPerPage(int offset, int limit) {
        List<Publication> publicationList = new ArrayList<>();
        String query = "SELECT * FROM publication LIMIT ?, ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Publication publication = new Publication();
                    publication.setId(rs.getInt("id"));
                    publication.setContenu(rs.getString("contenu"));
                    publication.setImage(rs.getString("image"));
                    publication.setDatepub(rs.getDate("datepub"));
                    publication.setTitre(rs.getString("titre"));
                    publicationList.add(publication);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Gérer l'exception correctement dans votre application
        }
        return publicationList;
    }
    public int getTotalPublicationCount() {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM publication";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Gérer l'exception correctement dans votre application
        }
        return totalCount;
    }

    public List<Publication> filtrerParDate(LocalDate date) throws SQLException {
        List<Publication> publicationsFiltrees = new ArrayList<>();
        String query = "SELECT * FROM publication WHERE DATE(datepub) = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, java.sql.Date.valueOf(date));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            publication.setDatepub(resultSet.getDate("datepub"));
            publicationsFiltrees.add(publication);
        }
        return publicationsFiltrees;
    }


}



