package Controllers.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;



import java.io.IOException;

public class ResetPasswordController {
    @FXML
    private PasswordField mdp1TF;

    @FXML
    private PasswordField mdp2TF;

    @FXML
    private Button confirmerTF;

    @FXML
    private Button retour_TF;

    @FXML
    public void initialize() {

    }

    @FXML
    private void Confirmer() {
        String password = mdp1TF.getText();
        String confirmPassword = mdp2TF.getText();

        System.out.println("Mot de passe: " + password);
        System.out.println("Confirmation du mot de passe: " + confirmPassword);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePageClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) confirmerTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

