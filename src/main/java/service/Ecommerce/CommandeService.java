package service.Ecommerce;
import model.Ecommerce.Commande;
import model.Ecommerce.Panier;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.List;

public class CommandeService  implements IService<Commande> {

    Connection connection;
    public  CommandeService(){
        connection= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void add(Commande commande) throws SQLException {
            String sql = "INSERT INTO commande (panier_id,adress, date_c,statut,methode_paiement,phone,methode_livraison) VALUES (?, ?, ?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commande.getIdPanier());
            preparedStatement.setString(2, commande.getAdresse());
            preparedStatement.setDate(3, commande.getDateC());
            preparedStatement.setString(4, commande.getStatut());
            preparedStatement.setString(5, commande.getMpaiement());
            preparedStatement.setString(6, commande.getPhone());
            preparedStatement.setString(7, commande.getMlivraison());
            preparedStatement.executeUpdate();
        }


    @Override
    public void update(Commande commande) throws SQLException {
        String sql ="update commande set adress=?,statut=? ,methode_paiement=?,phone=?,methode_livraison=? where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, commande.getAdresse());
        preparedStatement.setString(2, commande.getStatut());
        preparedStatement.setString(3, commande.getMpaiement());
        preparedStatement.setString(4, commande.getPhone());
        preparedStatement.setString(5, commande.getMlivraison());
        preparedStatement.setInt(6,commande.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Commande> select() throws SQLException {
        List<Commande>commandes=new ArrayList<>();
        String sql="select * from commande";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Commande commande =new Commande();
            commande.setId(resultSet.getInt("id"));
            commande.setIdPanier(resultSet.getInt("panier_id"));
            commande.setAdresse(resultSet.getString("adress"));
            commande.setStatut(resultSet.getString("statut"));
            commande.setDateC(resultSet.getDate("date_c"));
            commande.setMpaiement(resultSet.getString("methode_paiement"));
            commande.setPhone(resultSet.getString("phone"));
            commande.setMlivraison(resultSet.getString("methode_livraison"));
            commandes.add(commande);
        }
        return commandes;
    }
    }

