package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.events.Reservation;
import service.events.ReservationC;

import java.sql.SQLException;

public class ModifierResB {

    @FXML
    private TextField statut_paiementTF;

    @FXML
    private ComboBox<String> mode_paiementCombo;

    private Reservation reservationToModify;

    private ReservationC reservationC = new ReservationC();

    // Méthode pour définir la réservation à modifier
    public void setReservationToModify(Reservation reservation) {
        this.reservationToModify = reservation;
        // Affichez les informations de la réservation dans les champs correspondants
        statut_paiementTF.setText(reservation.getStatut_paiement());
        mode_paiementCombo.setValue(reservation.getMode_paiement());
    }

    @FXML
    void ModifierResB(ActionEvent event) {
        // Vérifiez si la réservation à modifier est définie
        if (reservationToModify != null) {
            String newStatutPaiement = statut_paiementTF.getText();
            String newModePaiement = mode_paiementCombo.getValue();
            // Vérifiez si les champs ne sont pas vides
            if (!newStatutPaiement.isEmpty() && newModePaiement != null) {
                try {
                    // Mettez à jour les informations de la réservation dans la base de données
                    reservationToModify.setStatut_paiement(newStatutPaiement);
                    reservationToModify.setMode_paiement(newModePaiement);
                    reservationC.update(reservationToModify);
                    // Affichez un message de succès
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Réservation modifiée avec succès");
                    alert.show();
                    // Fermez la fenêtre de modification
                    Stage stage = (Stage) statut_paiementTF.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    // Gérez les erreurs de base de données
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Erreur lors de la modification de la réservation : " + e.getMessage());
                    alert.show();
                }
            } else {
                // Affichez un message si l'un des champs est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.show();
            }
        } else {
            // Affichez un message si la réservation à modifier n'est pas définie
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Réservation à modifier non définie");
            alert.show();
        }
    }

    public void initData(Reservation selectedReservation) {
        // Assurez-vous que la réservation sélectionnée n'est pas nulle
        if (selectedReservation != null) {
            // Définissez la réservation à modifier avec la réservation sélectionnée
            reservationToModify = selectedReservation;

            // Affichez les informations de la réservation dans les champs correspondants
            statut_paiementTF.setText(selectedReservation.getStatut_paiement());
            mode_paiementCombo.setValue(selectedReservation.getMode_paiement());
        } else {
            // Affichez un message d'erreur si la réservation sélectionnée est nulle
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La réservation sélectionnée est nulle.");
            alert.show();
        }


    }
}