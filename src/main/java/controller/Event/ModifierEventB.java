package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.events.Evenement;
import service.events.EvenementC;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifierEventB {

    @FXML
    private TextField nomTF;

    @FXML
    private TextField dateDebutTF;

    @FXML
    private TextField dateFinTF;

    @FXML
    private TextField imageTF;

    @FXML
    private TextField localisationTF;

    @FXML
    private TextField montantTF;

    private Evenement evenement;

    private EvenementC ec = new EvenementC();

    public void initData(Evenement evenement) {
        this.evenement = evenement;
        // Pré-remplir les champs avec les informations de l'événement
        nomTF.setText(evenement.getNom());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateDebutTF.setText(sdf.format(evenement.getDateDebut()));
        dateFinTF.setText(sdf.format(evenement.getDateFin()));
        imageTF.setText(evenement.getImage());
        localisationTF.setText(evenement.getLocalisation());
        montantTF.setText(String.valueOf(evenement.getMontant()));
    }


    @FXML
    void modifier(ActionEvent event) {
        try {
            // Mise à jour des informations de l'événement
            evenement.setNom(nomTF.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDebut = sdf.parse(dateDebutTF.getText());
            evenement.setDateDebut(dateDebut);
            Date dateFin = sdf.parse(dateFinTF.getText());
            evenement.setDateFin(dateFin);
            evenement.setImage(imageTF.getText());
            evenement.setLocalisation(localisationTF.getText());
            evenement.setMontant(Float.parseFloat(montantTF.getText()));

            // Appel à la méthode de mise à jour dans le service EvenementC
            ec.update(evenement);

            // Affichage d'une confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Événement modifié avec succès");
            alert.show();

            // Charger la page d'affichage des événements
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherEventB.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle (Stage)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène de la fenêtre pour afficher la page d'affichage des événements
            stage.setScene(scene);
            stage.show();


        } catch (ParseException | SQLException | IOException e) {
            e.printStackTrace();
            // Gestion des erreurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to update event");
            alert.show();
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        // Fermeture de la fenêtre de modification sans effectuer de modification
        Stage stage = (Stage) nomTF.getScene().getWindow();
        stage.close();
    }
}
