package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.events.Evenement;
import model.events.Reservation;
import service.events.ReservationC;

import java.sql.SQLException;

public class AjouterResB {

    @FXML
    private TextField id_evenement_idTF;

    @FXML
    private TextField mode_paiementTF;

    @FXML
    private TextField statut_paiementTF;

    @FXML
    void AfficherResB(ActionEvent event) {

    }


    @FXML
    void AjouterResB(ActionEvent event) {
        int id_evenement_id = Integer.parseInt(id_evenement_idTF.getText());
        String statut_paiement = statut_paiementTF.getText();
        String mode_paiement = mode_paiementTF.getText();

        Reservation r = new Reservation();
        Evenement evenement = null;

        ReservationC rc = new ReservationC();

        try {
            // Vérifier si l'événement existe
            evenement = rc.getEvenementById(id_evenement_id);
            if (evenement != null) {
                r.setId_evenement_id(evenement);
                r.setStatut_paiement(statut_paiement);
                r.setMode_paiement(mode_paiement);

                rc.add(r);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Réservation ajoutée");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Cet événement n'existe pas");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
