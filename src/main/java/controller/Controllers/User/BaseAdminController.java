package controller.Controllers.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.control.Alert;

public class BaseAdminController {

    @FXML
    private AnchorPane AnchorPaneTF;
    @FXML
    private Pane PaneTF;
    @FXML
    private Button GererUtilisateursTF;
    @FXML
    private ImageView groupTF;
    @FXML
    private ImageView logoTF;
    @FXML
    private ChoiceBox<String> choiceBoxTF;

    @FXML
    private void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Se déconnecter", "Éditer le profil");
        choiceBoxTF.setItems(options);
        choiceBoxTF.setOnAction(event -> {
            String selectedItem = choiceBoxTF.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Se déconnecter")) {
                    redirectToHomePage();
                } else if (selectedItem.equals("Éditer le profil")) {
                    redirectToEditProfil();
                }
            }
        });
    }

    @FXML
    private void redirectToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectToEditProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/EditProfil.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger la page d'édition de profil");
            alert.setContentText("Veuillez réessayer ou contacter le support technique.");
            alert.showAndWait();
        }
    }

    @FXML
    private void gererUtilisateurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/GererUtilisateur.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) GererUtilisateursTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
