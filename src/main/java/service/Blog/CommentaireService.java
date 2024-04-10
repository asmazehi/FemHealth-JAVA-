package service.Blog;

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
        statement.setInt(2,commentaire.getUser_id());
        statement.setString(3,commentaire.getDescription());
        statement.setBoolean(4,false);
        statement.executeUpdate();
    }
    public void update(Commentaire commentaire) throws SQLException {
        String sql = "UPDATE commentaire SET publication=?, user_id=?, description=?, datecomnt=CURRENT_TIMESTAMP WHERE id=?";
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
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication")));
            commentaire.setUser_id(resultSet.getInt("user_id"));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }

    public Publication fetchPublicationById(int id) throws SQLException {
        String sql = "SELECT * FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Publication publication = new Publication();
            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            // Définir d'autres attributs de la publication si nécessaire
            return publication;
        }
        return null; // Retourne null si aucune publication trouvée avec cet ID
    }
}
