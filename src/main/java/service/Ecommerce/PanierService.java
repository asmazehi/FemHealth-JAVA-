package service.Ecommerce;


import com.mysql.cj.xdevapi.Table;
import model.Ecommerce.*;
import model.Sponsoring.Produit;
import service.Sponsoring.ProduitService;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanierService implements IService<Panier> {

    Connection connection;

    public PanierService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Panier panier) throws SQLException {
        connection.setAutoCommit(false);
        connection.commit();
        String sql = "INSERT INTO panier (client_id, prix_total, statut) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, panier.getIdUser());
        preparedStatement.setInt(2, panier.getPrixTotal());
        preparedStatement.setString(3, panier.getStatut());
        preparedStatement.executeUpdate();


    }

    @Override
    public void update(Panier panier) throws SQLException {
        String sql = "update panier set prix_total=?,statut=? where id=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, panier.getPrixTotal());
        preparedStatement.setString(2, panier.getStatut());
        preparedStatement.setInt(3, panier.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from panier where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public List<Panier> select() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String sql = "select * from panier";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Panier panier = new Panier();
            panier.setId(resultSet.getInt("id"));
            panier.setIdUser(resultSet.getInt("client_id"));
            panier.setPrixTotal(resultSet.getInt("prix_total"));
            panier.setStatut(resultSet.getString("statut"));
            paniers.add(panier);
        }
        return paniers;
    }

    public Panier selectPanierById(int id) {
        String sql = "SELECT * FROM panier WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Panier panier = new Panier();
                    panier.setId(resultSet.getInt("id"));
                    panier.setIdUser(resultSet.getInt("client_id"));
                    panier.setPrixTotal(resultSet.getInt("prix_total"));
                    panier.setStatut(resultSet.getString("statut"));
                    return panier;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Si aucun panier correspondant à l'ID n'est trouvé
    }

    public  List<PanierItem>  afficherinfopanier(int idpanier) {
        List<PanierItem> infosPanier = new ArrayList<>();
        LignepanierService lignepanierService = new LignepanierService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        Panier panier = null;

        // Récupérer le panier
        panier = this.selectPanierById(idpanier);

        if (panier != null) {
            // Récupérer les lignes de panier associées à ce panier
            List<Lignepanier> lignesPanier = lignepanierService.selectLignepanierByPanierId(idpanier);
            // Ajouter les informations de chaque produit dans le panier au tableau
            for (Lignepanier lignePanier : lignesPanier) {
                Produit produit = produitService.selectProduitById(lignePanier.getIdProduit());

                if (produit != null) {
                    // Récupérer les détails du produit
                    int idproduit=produit.getId();
                    String nomProduit = produit.getNom();
                    float prixUnitaire = produit.getPrix();
                    int quantite = lignePanier.getQuantité();
                    int totalpanier=panier.getPrixTotal();
                    String status=panier.getStatut();
                    PanierItem infopro= new PanierItem(idpanier,idproduit,nomProduit,prixUnitaire,quantite,totalpanier,status);
                    infosPanier.add(infopro);

                } else {
                    System.out.println("Produit non trouvé pour l'ID : " + lignePanier.getIdProduit());
                }
            }
        } else {
            System.out.println("Panier non trouvé pour l'ID : " + idpanier);
        }

        // Convertir la liste d'objets en un tableau d'objets et le retourner
        return infosPanier;
    }
    public int calculTotalPanier(int idPanier) {
        int total = 0;
        String sql = "SELECT SUM(prix * quantite) AS total FROM produit " +
                "INNER JOIN lignepanier ON produit.id = lignepanier.produit_id " +
                "WHERE lignepanier.panier_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idPanier);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }


    }







