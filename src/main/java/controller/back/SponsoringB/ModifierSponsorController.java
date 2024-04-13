package controller.back.SponsoringB;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ModifierSponsorController {
    @FXML
    private TextField nomS;

    @FXML
    private TextField dureeS;

    private Sponsor sponsor;
    private Stage afficherSponsorStage;

    public void setData(Sponsor sponsor, Stage afficherSponsorStage) {
        this.sponsor = sponsor;
        this.afficherSponsorStage = afficherSponsorStage;
        nomS.setText(sponsor.getNom());
        dureeS.setText(sponsor.getDuree_contrat());
    }

    private static boolean modifierSponsorPageOpen = false;

    @FXML
    void executerModification() {
        String nom = nomS.getText();
        String duree_contrat = dureeS.getText();
        sponsor.setNom(nom);
        sponsor.setDuree_contrat(duree_contrat);

        SponsorService ss = new SponsorService();
        try {
            ss.update(sponsor);

            // Close the current stage (ModifierSponsor)
            Stage modifierSponsorStage = (Stage) nomS.getScene().getWindow();
            modifierSponsorStage.close();

            // Close the AfficherSponsor page if it is open
            if (afficherSponsorStage != null) {
                afficherSponsorStage.close();
            }

            // Open a new instance of AfficherSponsor
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Afficher les sponsors");
            stage.show();

            // Set the new AfficherSponsor stage
            afficherSponsorStage = stage;
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}




