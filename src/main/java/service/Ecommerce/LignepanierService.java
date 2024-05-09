package service.Ecommerce;
import model.Ecommerce.Lignepanier;
import model.Ecommerce.Panier;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LignepanierService implements IService<Lignepanier>{
    Connection connection;
    public  LignepanierService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Lignepanier lignepanier) throws SQLException {
        String sql = "INSERT INTO lignepanier (panier_id, produit_id, quantite) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, lignepanier.getIdPanier());
        preparedStatement.setInt(2, lignepanier.getIdProduit());
        preparedStatement.setInt(3, lignepanier.getQuantité());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Lignepanier lignepanier) throws SQLException {
        String sql ="update lignepanier set quantite=? where panier_id=? AND produit_id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,lignepanier.getQuantité());
        preparedStatement.setInt(2,lignepanier.getIdPanier());
        preparedStatement.setInt(3,lignepanier.getIdProduit());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

    }
    public void delete(int idpanier, int idproduit) throws SQLException {
        String sql = "DELETE FROM lignepanier WHERE panier_id = ? AND produit_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idpanier);
            preparedStatement.setInt(2, idproduit);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public List<Lignepanier> select() throws SQLException {
        List<Lignepanier>lignepaniers=new ArrayList<>();
        String sql="select * from lignepanier";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Lignepanier lignepanier =new Lignepanier();
            lignepanier.setId(resultSet.getInt("id"));
            lignepanier.setQuantité(resultSet.getInt("quantite"));
            lignepaniers.add(lignepanier);
        }
        return lignepaniers;
    }

    public List<Lignepanier> selectLignepanierByPanierId(int idPanier) {
        List<Lignepanier> lignepaniers = new ArrayList<>();
        String sql = "SELECT * FROM lignepanier WHERE panier_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idPanier);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lignepanier lignepanier = new Lignepanier();
                    lignepanier.setId(resultSet.getInt("id"));
                    lignepanier.setQuantité(resultSet.getInt("quantite"));
                    lignepanier.setIdProduit(resultSet.getInt("produit_id"));
                    lignepanier.setIdPanier(idPanier); // Set the panier ID from the method parameter
                    lignepaniers.add(lignepanier);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lignepaniers;
    }


    public Lignepanier selectlignepanier(int idpanier, int idproduit) {
        String sql = "SELECT * FROM lignepanier WHERE panier_id = ? AND produit_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idpanier);
            preparedStatement.setInt(2, idproduit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { // Vérifie s'il y a une ligne correspondante
                    Lignepanier lignepanier = new Lignepanier();
                    lignepanier.setId(resultSet.getInt("id"));
                    lignepanier.setQuantité(resultSet.getInt("quantite"));
                    lignepanier.setIdPanier(idpanier);
                    lignepanier.setIdProduit(idproduit);
                    return lignepanier;
                } else {
                    return null; // Aucune ligne correspondante trouvée
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}