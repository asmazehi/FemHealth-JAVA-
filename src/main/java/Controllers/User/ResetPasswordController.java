package Controllers.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {
    @FXML
    private TextField mdp1TF;

    @FXML
    private TextField mdp2TF;

    @FXML
    private Button confirmerTF;

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
}

