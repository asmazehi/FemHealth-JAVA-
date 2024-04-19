package service.Blog;

import javafx.collections.ObservableList;
import model.Blog.Commentaire;
import model.Blog.Publication;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {
    Connection connection;
    public CommentaireService() {
        connection = MyDataBase.getInstance().getConnection();
    }
    public void add (Commentaire commentaire) throws SQLException {
        String sql = "INSERT INTO commentaire (publication_id, user_id, description, datecomnt,active) VALUES (?, ?, ?, CURRENT_TIMESTAMP,?)";    PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,commentaire.getPublication().getId());
        statement.setInt(2,3);
        statement.setString(3,commentaire.getDescription());
        statement.setBoolean(4,true);
        statement.executeUpdate();
    }
    public void update(Commentaire commentaire) throws SQLException {
        String sql = "UPDATE commentaire SET publication_id=?, user_id=?, description=?, datecomnt=CURRENT_TIMESTAMP WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, commentaire.getPublication().getId());
        statement.setInt(2, commentaire.getUser_id());
        statement.setString(3, commentaire.getDescription());
        statement.setInt(4, commentaire.getId());
        statement.executeUpdate();
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM commentaire WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    public List<Commentaire> select() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setUser_id(resultSet.getInt("user_id"));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }
    public List<Commentaire> fetchCommentaireByPublicationID(int id) throws SQLException {
        String sql="SELECT * FROM Commentaire WHERE publication_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Commentaire> listecommentaire = new ArrayList<>();
        while (resultSet.next()) {
            Commentaire commentaire= new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            commentaire.setUser_id(resultSet.getInt("user_id"));
            commentaire.setDescription(resultSet.getString("description"));

            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            listecommentaire.add(commentaire);
        }
        System.out.println("hrgjjh "+listecommentaire.size());
        return listecommentaire;
    }
    public Publication fetchPublicationById(int id) throws SQLException {
        String sql = "SELECT * FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Publication publication = new Publication();
        if (resultSet.next()) {
            System.out.println("dsfghjkl"+resultSet.getDate("datepub"));

            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));

            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            publication.setDatepub(resultSet.getDate("datepub"));

            System.out.println(resultSet.getString("image"));
        }
        System.out.println(publication.toString());
        return publication ;

    }
}
