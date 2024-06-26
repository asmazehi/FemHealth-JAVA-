package controller.Controllers.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import utils.Session;

public class BaseAdminController {

    @FXML
    private AnchorPane AnchorPaneTF;
    @FXML
    private ChoiceBox<String> Sponsor;
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
    private ChoiceBox<String> blogFx;

    @FXML
    private ChoiceBox<String> eventsFx;
    @FXML
    private Button idCommerce;

    @FXML
    private void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Se déconnecter", "Éditer le profil");
        ObservableList<String> optionsPublication = FXCollections.observableArrayList("Publications", "Commentaires");
        ObservableList<String> optionsEvents = FXCollections.observableArrayList("Events", "Reservation", "Type");
        ObservableList<String> optionsSponsor = FXCollections.observableArrayList("Produits", "Sponsor");
        Sponsor.setItems(optionsSponsor);
        Sponsor.setOnAction(event -> {
            String selectedItem = Sponsor.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Produits")) {
                    redirectToProduit();
                } else if (selectedItem.equals("Sponsor")) {
                    redirectToSponsor();
                }
            }
        });
        blogFx.setItems(optionsPublication);
        eventsFx.setItems(optionsEvents);
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
        blogFx.setOnAction(event -> {
            String selectedItem = blogFx.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Publications")) {
                    redirectToPublication();
                } else if (selectedItem.equals("Commentaires")) {
                    redirectToCommentaire();
                }
            }
        });
        eventsFx.setOnAction(event -> {
            String selectedItem = eventsFx.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Events")) {
                    redirectToEvents();
                } else if (selectedItem.equals("Reservation")) {
                    redirectToReservations();
                } else if (selectedItem.equals("Type")) {
                    redirectToType();

                }
            }
        });

    }

    private void redirectToSponsor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToProduit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherProduit.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherSponsor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToReservations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherResB.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToEvents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherEventB.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToPublication(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void redirectToCommentaire(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherCommentaire.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AnchorPaneTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void redirectToHomePage() {
        try {
            Session.clearSession();
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
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) AnchorPaneTF.getScene().getWindow();
            currentStage.close();


            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
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
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) GererUtilisateursTF.getScene().getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void CommerceAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Ecommerce/ShowAllCommands.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) GererUtilisateursTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}