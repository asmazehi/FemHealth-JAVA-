package controller.Sponsoring;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierSponsorController {

    @FXML
    private TextField nomS;

    @FXML
    private TextField dureeS;

    @FXML
    private Text erreurNom;

    @FXML
    private Text erreurDuree;

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

        // Vérifier la saisie
        String erreurNomText = controleNom(nom);
        String erreurDureeText = controleDuree(duree_contrat);

        // Afficher ou masquer les messages d'erreur
        erreurNom.setText(erreurNomText);
        erreurNom.setVisible(erreurNomText != null);

        erreurDuree.setText(erreurDureeText);
        erreurDuree.setVisible(erreurDureeText != null);

        if (erreurNomText != null || erreurDureeText != null) {
            return; // Il y a des erreurs, ne pas continuer
        }

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

    private String controleNom(String nom) {
        if (nom.isEmpty()) {
            return "Veuillez remplir le champ nom.";
        }

        if (nom.length() < 3) {
            return "Le nom du sponsor doit avoir au moins 3 lettres.";
        }

        return null; // Aucune erreur
    }

    private String controleDuree(String duree_contrat) {
        if (duree_contrat.isEmpty()) {
            return "Veuillez remplir le champ durée du contrat.";
        }

        if (!duree_contrat.matches("\\d{2} mois")) {
            return "La durée du contrat doit être sous la forme 'XX mois' (deux chiffres et le mot mois).";
        }

        return null; // Aucune erreur
    }
}
