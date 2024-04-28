package service.Sponsoring;

import utils.MyDataBase;
import model.Sponsoring.Sponsor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorService implements IService<Sponsor> {
    Connection connection;

    public SponsorService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Sponsor sponsor) throws SQLException {
        String sql = "INSERT INTO sponsor (nom, duree_contrat) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sponsor.getNom());
            statement.setString(2, sponsor.getDuree_contrat());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Sponsor sponsor) throws SQLException {
        String sql = "UPDATE sponsor SET nom=?, duree_contrat=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, sponsor.getNom());
            preparedStatement.setString(2, sponsor.getDuree_contrat());
            preparedStatement.setInt(3, sponsor.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        // Delete associated produits first
        String deleteProduitSql = "DELETE FROM produit WHERE sponsor_id=?";
        try (PreparedStatement deleteProduitStatement = connection.prepareStatement(deleteProduitSql)) {
            deleteProduitStatement.setInt(1, id);
            deleteProduitStatement.executeUpdate();
        }

        // Delete sponsor
        String sql = "DELETE FROM sponsor WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Sponsor> select() throws SQLException {
        List<Sponsor> sponsors = new ArrayList<>();
        String sql = "SELECT * FROM sponsor";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Sponsor sponsor = new Sponsor();
                sponsor.setId(resultSet.getInt("id"));
                sponsor.setNom(resultSet.getString("nom"));
                sponsor.setDuree_contrat(resultSet.getString("duree_contrat"));
                sponsors.add(sponsor);
            }
        }
        return sponsors;
    }
}
