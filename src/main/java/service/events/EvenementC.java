package service.events;

import utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.events.Evenement;
import model.events.Type;

public class EvenementC implements IEvenement<Evenement> {
    Connection connection;

    public EvenementC() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public static Type getType_id(int typeIdValue) throws SQLException {
        Connection connection = MyDataBase.getInstance().getConnection();
        String sql = "SELECT * FROM Type WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, typeIdValue);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type");
            return new Type(id, type);
        } else {
            throw new SQLException("Type with ID " + typeIdValue + " not found");
        }
    }

    @Override
    public void add(Evenement evenement) throws SQLException {
        String sql = "INSERT INTO Evenement (type_id,nom, date_debut, date_fin, image, localisation, montant) VALUES (?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, evenement.getType_id().getId());
        preparedStatement.setString(2, evenement.getNom());
        preparedStatement.setDate(3, new Date(evenement.getDateDebut().getTime()));
        preparedStatement.setDate(4, new Date(evenement.getDateFin().getTime()));
        preparedStatement.setString(5, evenement.getImage());
        preparedStatement.setString(6, evenement.getLocalisation());
        preparedStatement.setFloat(7, evenement.getMontant());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Evenement evenement) throws SQLException {
        String sql = "UPDATE Evenement SET type_id=?, nom=?, date_debut=?, date_fin=?, image=?, localisation=?, montant=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, evenement.getType_id().getId()); // Utilise la clé étrangère type_id à partir de l'objet Type associé
        preparedStatement.setString(2, evenement.getNom());
        preparedStatement.setDate(3, new Date(evenement.getDateDebut().getTime()));
        preparedStatement.setDate(4, new Date(evenement.getDateFin().getTime()));
        preparedStatement.setString(5, evenement.getImage());
        preparedStatement.setString(6, evenement.getLocalisation());
        preparedStatement.setFloat(7, evenement.getMontant());
        preparedStatement.setInt(8, evenement.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Evenement WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Evenement> select() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM Evenement";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(resultSet.getInt("id"));
            evenement.setNom(resultSet.getString("nom"));
            evenement.setDateDebut(resultSet.getDate("date_debut"));
            evenement.setDateFin(resultSet.getDate("date_fin"));
            evenement.setImage(resultSet.getString("image"));
            evenement.setLocalisation(resultSet.getString("localisation"));
            evenement.setMontant(resultSet.getFloat("montant"));

            // Récupère la référence du type à partir de son ID et l'associe à l'événement
            int typeId = resultSet.getInt("type_id");
            TypeC typeService = new TypeC();
            Type type = typeService.selectById(typeId);
            evenement.setType_id(type);

            evenements.add(evenement);
        }
        return evenements;
    }
    public List<Evenement> searchByName(String name) throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM Evenement WHERE nom LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Evenement evenement = new Evenement();
                    evenement.setId(resultSet.getInt("id"));
                    evenement.setNom(resultSet.getString("nom"));
                    evenement.setDateDebut(resultSet.getDate("date_debut"));
                    evenement.setDateFin(resultSet.getDate("date_fin"));
                    evenement.setImage(resultSet.getString("image"));
                    evenement.setLocalisation(resultSet.getString("localisation"));
                    evenement.setMontant(resultSet.getFloat("montant"));

                    // Récupère la référence du type à partir de son ID et l'associe à l'événement
                    int typeId = resultSet.getInt("type_id");
                    TypeC typeService = new TypeC();
                    Type type = typeService.selectById(typeId);
                    evenement.setType_id(type);

                    evenements.add(evenement);
                }
            }
        }
        return evenements;
    }
    public Evenement getEventInformationFromDatabase(int eventId) throws SQLException {
        Evenement evenement = null;
        String sql = "SELECT * FROM Evenement WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, eventId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    evenement = new Evenement();
                    evenement.setId(resultSet.getInt("id"));
                    evenement.setNom(resultSet.getString("nom"));
                    evenement.setDateDebut(resultSet.getDate("date_debut"));
                    evenement.setDateFin(resultSet.getDate("date_fin"));
                    evenement.setImage(resultSet.getString("image"));
                    evenement.setLocalisation(resultSet.getString("localisation"));
                    evenement.setMontant(resultSet.getFloat("montant"));

                    int typeId = resultSet.getInt("type_id");
                    TypeC typeService = new TypeC();
                    Type type = typeService.selectById(typeId);
                    evenement.setType_id(type);
                }
            }
        }
        return evenement;
    }
    public List<Evenement> selectWithReservationCount() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT e.*, COUNT(r.id_evenement_id) AS reservation_count FROM Evenement e LEFT JOIN Reservation r ON e.id = r.id_evenement_id GROUP BY e.id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(resultSet.getInt("id"));
            evenement.setNom(resultSet.getString("nom"));
            evenement.setDateDebut(resultSet.getDate("date_debut"));
            evenement.setDateFin(resultSet.getDate("date_fin"));
            evenement.setImage(resultSet.getString("image"));
            evenement.setLocalisation(resultSet.getString("localisation"));
            evenement.setMontant(resultSet.getFloat("montant"));
            evenement.setReservationCount(resultSet.getInt("reservation_count")); // Set the reservation count

            // Récupère la référence du type à partir de son ID et l'associe à l'événement
            int typeId = resultSet.getInt("type_id");
            TypeC typeService = new TypeC();
            Type type = typeService.selectById(typeId);
            evenement.setType_id(type);

            evenements.add(evenement);
        }
        return evenements;
    }
    public List<String> getAllTypes() throws SQLException {
        List<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT type FROM Type"; // Sélectionne tous les types distincts
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                types.add(resultSet.getString("type")); // Ajoute chaque type à la liste
            }
        }
        return types;
    }
    public List<Evenement> getEventsByType(String type) throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM Evenement WHERE type_id = (SELECT id FROM Type WHERE type = ? LIMIT 1)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, type);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Evenement evenement = new Evenement();
                    evenement.setId(resultSet.getInt("id"));
                    evenement.setNom(resultSet.getString("nom"));
                    evenement.setDateDebut(resultSet.getDate("date_debut"));
                    evenement.setDateFin(resultSet.getDate("date_fin"));
                    evenement.setImage(resultSet.getString("image"));
                    evenement.setLocalisation(resultSet.getString("localisation"));
                    evenement.setMontant(resultSet.getFloat("montant"));

                    // Vous pouvez également définir le type ici si nécessaire

                    evenements.add(evenement);
                }
            }
        }
        return evenements;
    }

}
