package controller.Controllers.User;

import controller.Sponsoring.AfficherProduitFrontController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User.Utilisateur;

import java.io.IOException;

public class HomePageClientController {
    @FXML
    private Button testprod;

    @FXML
    private Button gererProfilButton;

    @FXML
    private Button seDeconnecterButton;

    @FXML
    private void initialize() {

    }

    private Utilisateur CurrentUser;

    public void SetData(Utilisateur user){
        this.CurrentUser = user;
    }

    @FXML
    private void gererProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/EditProfil.fxml"));
            Parent root = loader.load();

            EditProfilController controller = loader.getController();
            controller.setData(CurrentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void seDeconnecter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) seDeconnecterButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void testprod(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Sponsoring/AfficherProduitF.fxml"));
            Parent root = loader.load();
            AfficherProduitFrontController controller = loader.getController();

            testprod.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }

    }
}
