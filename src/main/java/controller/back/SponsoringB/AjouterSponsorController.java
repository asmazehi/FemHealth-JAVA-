package controller.back.SponsoringB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;

import java.sql.SQLException;

public class AjouterSponsorController {

    @FXML
    private TextField dureeS;

    @FXML
    private TextField nomS;

    @FXML
    void afficherSponsors(ActionEvent event) {

    }

    @FXML
    void ajouterSponsor(ActionEvent event) {
        String nom = nomS.getText();
        String duree_contrat = dureeS.getText();
        Sponsor s = new Sponsor(nom,duree_contrat);
        SponsorService ss =new SponsorService();
        try {
            ss.add(s);
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Sponsor ajouté");
            alert.show();
        } catch (SQLException e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

}
