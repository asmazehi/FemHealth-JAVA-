package service.Blog;



import model.Blog.Commentaire;
import model.Blog.Publication;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import javax.mail.Session;

import java.util.ArrayList;
import java.util.List;

import model.User.Utilisateur;
import utils.MyDataBase;



public class CommentaireService {
    Connection connection;


    public CommentaireService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public void add(Commentaire commentaire) throws SQLException {
        String sql = "INSERT INTO commentaire (publication_id, user_id, description, datecomnt,active) VALUES (?, ?, ?, CURRENT_TIMESTAMP,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, commentaire.getPublication().getId());
        statement.setInt(2, utils.Session.getSession().getUser().getId());
        statement.setString(3, commentaire.getDescription());
        statement.setBoolean(4, true);
        statement.executeUpdate();
    }

    public void update(Commentaire commentaire) throws SQLException {

        String sql = "UPDATE commentaire SET  description=?, datecomnt=CURRENT_TIMESTAMP WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, commentaire.getDescription());
        statement.setInt(2, commentaire.getId());


        statement.executeUpdate();
    }
    public void ActiveDesactiveComentaire(Commentaire commentaire) throws SQLException {
 try{

        String sql = "UPDATE commentaire SET  active=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setBoolean(1, commentaire.isActive());
        statement.setInt(2, commentaire.getId());
     System.out.println("iduser user"+commentaire.getUser_id().getId());
        statement.executeUpdate();

     Properties props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "587");

     Session session = Session.getInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication("chaimatlili62@gmail.com", "bxra lvjy ajes ajqs");
         }
     });

     Message message = new MimeMessage(session);
     message.setFrom(new InternetAddress("chaimatlili62@gmail.com"));
     message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(commentaire.getUser_id().getEmail()));
     message.setSubject("Votre comentaire");
     if(commentaire.isActive()==true){
         message.setText("Bonjour "+ commentaire.getUser_id().getNom()+" votre commentaire est activé ");
     }
     if(commentaire.isActive()==false){
         message.setText("Bonjour "+ commentaire.getUser_id().getNom()+" votre commentaire est desactivé ");
     }

     session.setDebug(true);
     Transport.send(message);
     System.out.println("message sent successfully....");

 }catch (Exception e){
     e.printStackTrace();
 }
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
            commentaire.setUser_id(fetchUtilisateurById(resultSet.getInt("user_id")));
            commentaire.setActive(resultSet.getBoolean("active"));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            commentaires.add(commentaire);
        }
        return commentaires;
    }
    public void incrimentelike(Commentaire comentaire){
        try {
            System.out.println( "update"+comentaire.getBrlike());
            System.out.println( "updateid"+comentaire.getId());

            String sql = "UPDATE commentaire SET  likes=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, comentaire.getBrlike());
            statement.setInt(2, comentaire.getId());

            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Commentaire> fetchCommentaireByPublicationID(int id) throws SQLException {
        String sql = "SELECT * FROM Commentaire WHERE publication_id=?  ";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Commentaire> listecommentaire = new ArrayList<>();
        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setUser_id(fetchUtilisateurById(resultSet.getInt("user_id")));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            commentaire.setActive(resultSet.getBoolean("active"));
           commentaire.setBrlike(resultSet.getInt("likes"));
            listecommentaire.add(commentaire);
        }
        System.out.println(listecommentaire.size());
        return listecommentaire;
    }

    public List<Commentaire> fetchCommentaireByID(int id) throws SQLException {
        String sql = "SELECT * FROM Commentaire WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Commentaire> listecommentaire = new ArrayList<>();
        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setUser_id(fetchUtilisateurById(resultSet.getInt("user_id")));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            listecommentaire.add(commentaire);
        }
        System.out.println(listecommentaire.size());
        return listecommentaire;
    }

    public List<Commentaire> fetchCommentaireByUserID(int id) throws SQLException {
        String sql = "SELECT * FROM Commentaire WHERE user_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Commentaire> listecommentaire = new ArrayList<>();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("publication_id"));
            Commentaire commentaire = new Commentaire();
            commentaire.setId(resultSet.getInt("id"));
            System.out.println(fetchPublicationTitleById(resultSet.getInt("publication_id")));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setActive((resultSet.getBoolean("active")));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));
            listecommentaire.add(commentaire);
        }

        return listecommentaire;
    }

    public Publication fetchPublicationById(int id) throws SQLException {
        String sql = "SELECT * FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Publication publication = new Publication();
        if (resultSet.next()) {
            System.out.println(resultSet.getDate("datepub"));

            publication.setId(resultSet.getInt("id"));
            publication.setTitre(resultSet.getString("titre"));
            publication.setContenu(resultSet.getString("contenu"));
            publication.setImage(resultSet.getString("image"));
            publication.setDatepub(resultSet.getDate("datepub"));
            System.out.println(resultSet.getString("image"));
        }
        System.out.println(publication.toString());
        return publication;

    }

    public String fetchPublicationTitleById(int id) throws SQLException {
        String sql = "SELECT titre FROM Publication WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String titre = null;
        if (resultSet.next()) {
            titre = resultSet.getString("titre");
        }
        return titre;
    }
    public String fetchUserNomById(int id) throws SQLException {
        String sql = "SELECT titre FROM User WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String nom = null;
        if (resultSet.next()) {
            nom = resultSet.getString("nom");
        }
        return nom;
    }
    public List<Commentaire> fetchCommentaireByCommentDescription(String description) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM Commentaire WHERE description LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + description + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Commentaire commentaire = new Commentaire();

            commentaire.setId(resultSet.getInt("id"));
            System.out.println(fetchPublicationTitleById(resultSet.getInt("publication_id")));
            commentaire.setPublication(fetchPublicationById(resultSet.getInt("publication_id")));
            commentaire.setDescription(resultSet.getString("description"));
            commentaire.setDatecomnt(resultSet.getDate("datecomnt"));

            commentaires.add(commentaire);
        }

        return commentaires;
    }
    public Utilisateur fetchUtilisateurById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Utilisateur utilisateur = new Utilisateur();
        if (resultSet.next()) {
            utilisateur.setId(resultSet.getInt("id"));
            utilisateur.setNom(resultSet.getString("nom"));
            utilisateur.setEmail(resultSet.getString("email"));
            utilisateur.setMdp(resultSet.getString("password"));
            utilisateur.setRole(resultSet.getString("roles"));
            utilisateur.setActive(resultSet.getInt("active"));
            utilisateur.setRegistred_at(resultSet.getDate("registered_at"));
        }
        return utilisateur;
    }

}