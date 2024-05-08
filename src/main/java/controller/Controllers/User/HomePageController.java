package controller.Controllers.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Button loginButton;

    @FXML
    private Button signFX;

    @FXML
    private ImageView logoImageView;




    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Inscription.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLoginInButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Authentification.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Initialisation de la page d'accueil...");
    }
}