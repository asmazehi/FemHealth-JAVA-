package Controllers.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditProfilController {
    @FXML
    private TextField EmailTF;

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
    private void initialize() {

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
        String email = EmailTF.getText();
        String motDePasseActuel = mdpAC_TF.getText();
        String nouveauMotDePasse = mdpNV_TF.getText();
        String confirmationMotDePasse = mdpC_TF.getText();

        // Vérification de la saisie
        if (!validerSaisie(email, motDePasseActuel, nouveauMotDePasse, confirmationMotDePasse)) {
            return;
        }

        // Enregistrement du nouveau mot de passe dans la base de données
        // Insérez ici votre logique pour enregistrer le nouveau mot de passe

        // Redirection vers la page d'accueil
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePageClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ConfirmationTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validerSaisie(String email, String motDePasseActuel, String nouveauMotDePasse, String confirmationMotDePasse) {

        if (!motDePasseActuel.equals("MotDePasseActuel")) {
            showAlert("Mot de passe incorrect", "Le mot de passe actuel est incorrect.");
            return false;
        }


        if (nouveauMotDePasse.length() < 6) {
            showAlert("Format incorrect", "Le nouveau mot de passe doit comporter au moins 6 caractères.");
            return false;
        }


        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            showAlert("Confirmation invalide", "La confirmation du mot de passe ne correspond pas au nouveau mot de passe.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
