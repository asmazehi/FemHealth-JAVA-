package service.Sponsoring;

import model.Sponsoring.Produit;
import model.Sponsoring.Sponsor;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IService<Produit> {
    Connection connection;

    public ProduitService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Produit produit) throws SQLException {
        String sql = "insert into produit (nom, prix, taux_remise, categorie, image, description, sponsor_id) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, produit.getNom());
        statement.setFloat(2, produit.getPrix());
        statement.setInt(3, produit.getTaux_remise());
        statement.setString(4, produit.getCategorie());
        statement.setString(5, produit.getImage());
        statement.setString(6, produit.getDescription());
        statement.setInt(7, produit.getSponsor().getId());
        statement.executeUpdate();
    }

    @Override
    public void update(Produit produit) throws SQLException {
        String sql = "update produit set nom=?, prix=?, taux_remise=?, categorie=?, image=?, description=?, sponsor_id=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getNom());
        preparedStatement.setFloat(2, produit.getPrix());
        preparedStatement.setInt(3, produit.getTaux_remise());
        preparedStatement.setString(4, produit.getCategorie());
        preparedStatement.setString(5, produit.getImage());
        preparedStatement.setString(6, produit.getDescription());
        preparedStatement.setInt(7, produit.getSponsor().getId());
        preparedStatement.setInt(8, produit.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from produit where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Produit> select() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "select * from produit";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Produit produit = new Produit();
            produit.setId(resultSet.getInt("id"));
            produit.setNom(resultSet.getString("nom"));
            produit.setPrix(resultSet.getFloat("prix"));
            produit.setTaux_remise(resultSet.getInt("taux_remise"));
            produit.setCategorie(resultSet.getString("categorie"));
            produit.setImage(resultSet.getString("image"));
            produit.setDescription(resultSet.getString("description"));
            // Fetch and set Sponsor
            int sponsorId = resultSet.getInt("sponsor_id");
            Sponsor sponsor = fetchSponsorById(sponsorId);
            produit.setSponsor(sponsor);
            produits.add(produit);
        }
        return produits;
    }

    private Sponsor fetchSponsorById(int sponsorId) throws SQLException {
        String sql = "select * from sponsor where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sponsorId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Sponsor sponsor = new Sponsor();
            sponsor.setId(resultSet.getInt("id"));
            sponsor.setNom(resultSet.getString("nom"));
            sponsor.setDuree_contrat(resultSet.getString("duree_contrat"));
            return sponsor;
        }
        return null;
    }




    //ketbethaa iness afsa5 cmntr ki tchouffou :')

    public Produit selectProduitById(int idProduit)  {
        Produit produit = null;
        String sql = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idProduit);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produit = new Produit();
                    produit.setId(resultSet.getInt("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getFloat("prix"));
                    produit.setTaux_remise(resultSet.getInt("taux_remise"));
                    produit.setCategorie(resultSet.getString("categorie"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setDescription(resultSet.getString("description"));

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produit;
    }

    public List<Produit> selectByCategory(String category) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE categorie = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getFloat("prix"));
                    produit.setTaux_remise(resultSet.getInt("taux_remise"));
                    produit.setCategorie(resultSet.getString("categorie"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setDescription(resultSet.getString("description"));
                    int sponsorId = resultSet.getInt("sponsor_id");
                    Sponsor sponsor = fetchSponsorById(sponsorId);
                    produit.setSponsor(sponsor);
                    produits.add(produit);
                }
            }
        }
        return produits;
    }

}