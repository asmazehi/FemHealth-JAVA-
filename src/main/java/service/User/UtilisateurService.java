package service.User;

import model.User.Utilisateur;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IService<Utilisateur> {

    Connection connection;


    public UtilisateurService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public Utilisateur afficheUser(int id) {
        Utilisateur p = new Utilisateur();
        try {
            String req = "Select * from  `utilisateur` where id=" + id;
            Statement st = connection.createStatement();
            ResultSet RS = st.executeQuery(req);
            RS.next();
            p.setId(RS.getInt("id"));
            p.setNom(RS.getString("nom"));
            p.setMail(RS.getString("mail"));
            p.setMdp(RS.getString("mdp"));
            p.setRole(RS.getString("role"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }
    public Object authentification(String mail, String mdp) {
        Utilisateur p = new Utilisateur();
        try {
            String req = "Select * from  `utilisateur` where mail ='" + mail + "' AND mdp ='" + mdp + "'";
            Statement st = connection.createStatement();
            System.out.println(req);
            ResultSet RS = st.executeQuery(req);
            RS.next();
            p.setId(RS.getInt("id"));
            p.setNom(RS.getString("nom"));
            p.setMail(RS.getString("mail"));
            p.setMdp(RS.getString("mdp"));
            p.setRole(RS.getString("role"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;


        //class ValidationUtil {

            //public static boolean estChaineValide(String chaine) {
                // Vérifier si la chaîne est vide ou nulle
                //if (chaine == null || chaine.trim().isEmpty()) {
                    //return false;
                //}

                // Vérifier si la chaîne ne contient que des lettres
                //if (!chaine.matches("[a-zA-Z ]+")) {
                 //   return false;
                //}

                // La chaîne est valide si elle passe toutes les vérifications
                //return true;
            //}

            ////public boolean isStringLength(String str) {
               // return str.length() < 8;
            //}





        }


    //}

    @Override
    public void add(Utilisateur utilisateur) throws SQLException {
        try {
            String req = "INSERT INTO `utilisateur`(`id`, `nom`, `mail`, `mdp`, `role`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(req);
            statement.setInt(1, utilisateur.getId());
            statement.setString(2, utilisateur.getNom());
            statement.setString(3, utilisateur.getMail());
            statement.setString(4, utilisateur.getMdp());
            statement.setString(5, utilisateur.getRole());
            statement.executeUpdate();
            System.out.println("Utilisateur inséré");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }





    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        try {
            String req = "UPDATE `utilisateur` SET `nom` = ?, `mail` = ?, `mdp` = ?, `role` = ? WHERE `id` = ?";
            PreparedStatement statement = connection.prepareStatement(req);
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getMail());
            statement.setString(3, utilisateur.getMdp());
            statement.setString(4, utilisateur.getRole());
            statement.setInt(5, utilisateur.getId());
            statement.executeUpdate();
            System.out.println("Utilisateur mis à jour");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String req = "DELETE FROM `utilisateur` WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(req);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Utilisateur supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Utilisateur> select() throws SQLException {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "Select * from  `utilisateur` order by id";
            Statement st = connection.createStatement();

            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                Utilisateur p = new Utilisateur();
                p.setId(RS.getInt("id"));
                p.setNom(RS.getString("nom"));
                p.setMail(RS.getString("mail"));
                p.setMdp(RS.getString("mdp"));
                p.setRole(RS.getString("role"));
                list.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

}