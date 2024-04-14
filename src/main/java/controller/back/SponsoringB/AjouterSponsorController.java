package controller.back.SponsoringB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterSponsorController {

    @FXML
    private TextField dureeS;

    @FXML
    private TextField nomS;

    private static boolean afficherSponsorPageOpen = false;

    @FXML
    void afficherSponsor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            Parent root = loader.load();
            AfficherSponsorController controller = loader.getController();
            controller.setData(nomS.getText());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher les sponsors");
            stage.setOnCloseRequest(e -> {
                afficherSponsorPageOpen = false; // Reset the flag in the AjouterSponsorController
            });
            stage.show();

            afficherSponsorPageOpen = true;

            // Close the current stage
            Stage currentStage = (Stage) nomS.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue AfficherSponsor.fxml: " + e.getMessage());
        }
    }




    @FXML
    void ajouterSponsor(ActionEvent event) {
        String nom = nomS.getText();
        String duree_contrat = dureeS.getText();
        Sponsor s = new Sponsor(nom, duree_contrat);
        SponsorService ss = new SponsorService();
        try {
            ss.add(s);

            // Load and show AfficherSponsor page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            Parent root = loader.load();
            AfficherSponsorController controller = loader.getController();
            controller.setData(nom);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher les sponsors");
            stage.show();

            // Close the current stage (AjouterSponsor)
            Stage currentStage = (Stage) nomS.getScene().getWindow();
            currentStage.close();
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


}
