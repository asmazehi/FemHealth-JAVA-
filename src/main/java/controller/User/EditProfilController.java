package controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import model.User.Utilisateur;
import service.User.UtilisateurService;
import utils.PasswordUtils;

public class EditProfilController {

    public Label labelEmailFX;
//    @FXML
//    private TextField EmailTF;

    @FXML
    private CheckBox changemdp;

    @FXML
    private PasswordField mdpAC_TF;

    @FXML
    private PasswordField mdpNV_TF;

    @FXML
    private PasswordField mdpC_TF;

    @FXML
    private Button ConfirmationTF;

    @FXML
    private Button retour_TF;

    private Utilisateur user;
    private Utilisateur currentUser;
    private UtilisateurService utilisateurService;

    @FXML
    private void initialize() {
        utilisateurService = new UtilisateurService();
    }

    public void setData(Utilisateur currentUser){
        this.currentUser = currentUser;
        labelEmailFX.setText(currentUser.getEmail());
    }

    @FXML
    private void Modifiermdp() {
        if (changemdp.isSelected()) {
            mdpAC_TF.setVisible(true);
            mdpNV_TF.setVisible(true);
            mdpC_TF.setVisible(true);
        } else {
            mdpAC_TF.setVisible(false);
            mdpNV_TF.setVisible(false);
            mdpC_TF.setVisible(false);
        }
    }

    @FXML
    private void ModifierInfo() {

        String motDePasseActuel = mdpAC_TF.getText();
        String nouveauMotDePasse = mdpNV_TF.getText();
        String confirmationMotDePasse = mdpC_TF.getText();

        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            showAlert("Confirmation invalide", "La confirmation du mot de passe ne correspond pas au nouveau mot de passe.");
            return;
        }

        if (motDePasseActuel.isEmpty() || nouveauMotDePasse.isEmpty() || confirmationMotDePasse.isEmpty()) {
            showAlert("Champs requis", "Veuillez remplir tous les champs.");
            return;
        }

        if (PasswordUtils.verifyPassword(motDePasseActuel, currentUser.getMdp())) {
            if (nouveauMotDePasse.equals(confirmationMotDePasse)) {
                currentUser.setMdp(PasswordUtils.hashPasswrd(nouveauMotDePasse));
                try {
                    utilisateurService.update(currentUser);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePageClient.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ConfirmationTF.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Confirmation invalide", "La confirmation du mot de passe ne correspond pas au nouveau mot de passe.");
            }
        } else {
            showAlert("Mot de passe incorrect", "Le mot de passe actuel est incorrect.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Authentification.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour_TF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}