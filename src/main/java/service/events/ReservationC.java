package service.events;

import utils.MyDataBase;
import model.events.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.events.Evenement;

public class ReservationC implements IReservation<Reservation> {
    Connection connection;

    public ReservationC() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation (id_evenement_id, statut_paiement, mode_paiement) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, reservation.getId_evenement_id().getId());
        preparedStatement.setString(2,"en attente");
        preparedStatement.setString(3, reservation.getMode_paiement());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE Reservation SET id_evenement_id=?, statut_paiement=?, mode_paiement=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, reservation.getId_evenement_id().getId());
        preparedStatement.setString(2, "en attente");
        preparedStatement.setString(3, reservation.getMode_paiement());
        preparedStatement.setInt(4, reservation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Reservation WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }



    public Evenement getEvenementById(int evenementId) throws SQLException {
        String sql = "SELECT * FROM Evenement WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, evenementId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(resultSet.getInt("id"));
            evenement.setNom(resultSet.getString("nom"));
            evenement.setDateDebut(resultSet.getDate("date_debut"));
            evenement.setDateFin(resultSet.getDate("date_fin"));
            evenement.setImage(resultSet.getString("image"));
            evenement.setLocalisation(resultSet.getString("localisation"));
            evenement.setMontant(resultSet.getFloat("montant"));

            return evenement;
        }

        return null; // Retourne null si aucun événement correspondant à l'ID n'est trouvé
    }
    @Override
    public List<Reservation> select() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getInt("id"));
            int evenementId = resultSet.getInt("id_evenement_id");
            Evenement evenement = getEvenementById(evenementId); // Remplacez getEvenementById par la méthode appropriée pour récupérer un objet Evenement par son ID
            reservation.setId_evenement_id(evenement);
            reservation.statut_paiement = "en attente";
            reservation.setMode_paiement(resultSet.getString("mode_paiement"));
            reservations.add(reservation);
        }
        return reservations;
    }
}
