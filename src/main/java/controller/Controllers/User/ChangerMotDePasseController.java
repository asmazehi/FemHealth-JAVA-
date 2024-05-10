package controller.Controllers.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.User.UtilisateurService;
import utils.EmailUtils;

import javax.mail.MessagingException;
import java.io.IOException;

public class ChangerMotDePasseController {

    @FXML
    private TextField emailTF;

    @FXML
    private Button confirmerTF;

    @FXML
    private Button retour_TF;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void Confirmer() {
        String email = emailTF.getText();
        if (email.isEmpty()) {
            afficherAlerte("Veuillez entrer votre adresse e-mail.");
            return;
        }
        if (!emailExiste(email)) {
            afficherAlerte("Cet e-mail n'est pas enregistré dans notre base de données.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ResetPassword.fxml"));
            Parent root = loader.load();
            ResetPasswordController resetPasswordController = loader.getController();
            resetPasswordController.setEmail(email);
            Stage currentStage = (Stage) retour_TF.getScene().getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Authentification.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) retour_TF.getScene().getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean emailExiste(String email) {
        return utilisateurService.emailExiste(email);
    }




}