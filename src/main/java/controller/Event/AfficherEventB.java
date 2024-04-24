package controller.Event;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEventB {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label reservationsLabel;

    @FXML
    private TableColumn<Evenement, Date> date_debutCol;

    @FXML
    private TableColumn<Evenement, Date> date_finCol;

    @FXML
    private TableColumn<Evenement, String> imageCol;

    @FXML
    private TableColumn<Evenement, String> localisationCol;

    @FXML
    private TableColumn<Evenement, Integer> montantCol;

    @FXML
    private TableColumn<Evenement, String> nomCol;

    @FXML
    private TableView<Evenement> tableview;

    @FXML
    private TableColumn<Evenement, Type> type_idCol;

    @FXML
    private TableColumn<Evenement, Void> reservationCol;

    @FXML
    private Label welcomelb;

    private EvenementC ec = new EvenementC();
    private ObservableList<Evenement> obs;

    public void ajouterEventB(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back.Event/AjouterEventB.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Optionally, close the current window if needed
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de la vue AjouterFXML.fxml
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load AjouterFXML.fxml");
            alert.show();
        }

    }

    @FXML
    void supprimerEventB(ActionEvent event) {
        try {
            Evenement e = tableview.getSelectionModel().getSelectedItem();
            ec.delete(e.getId());
            obs.remove(e);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void modifierEventB(ActionEvent event) {
        // Récupérer l'événement sélectionné dans le TableView
        Evenement evenement = tableview.getSelectionModel().getSelectedItem();

        if (evenement != null) {
            try {
                // Charger la vue de modification avec le contrôleur correspondant
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/ModifierEventB.fxml"));
                Parent root = loader.load();

                // Passer l'événement à modifier au contrôleur de modification
                ModifierEventB controller = loader.getController();
                controller.initData(evenement);

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle et changer sa racine
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur de chargement de la vue de modification
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to load ModifierEventB.fxml");
                alert.show();
            }
        } else {
            // Afficher un message si aucun événement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez sélectionner un événement à modifier");
            alert.show();
        }
    }


    @FXML
    void initialize() {
        try {
            List<Evenement> list = ec.select();
            obs = FXCollections.observableArrayList(list);
            tableview.setItems(obs);
            //type_idCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            type_idCol.setCellValueFactory(cellData -> {
                Type type = cellData.getValue().getType_id();
               // String typeName = (type != null) ? type.getType() : "";
                return new ReadOnlyObjectWrapper<>(type);            });

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            localisationCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
            date_debutCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            date_finCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

            reservationCol.setCellFactory(cell -> new TableCell<Evenement, Void>() {
                private final Button reservationButton = new Button("Réserver");

                {
                    reservationButton.setOnAction(event -> {
                        Evenement evenement = getTableRow().getItem();
                        openReservationPage(evenement.getId());
                    });

                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(reservationButton);
                    }
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void openReservationPage(int eventId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back.Event/AjouterResB.fxml"));
            Parent root = loader.load();

            // Pass the event ID to the controller for AjouterResB.fxml
            AjouterResB controller = loader.getController();
            controller.initData(eventId);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load AjouterResB.fxml");
            alert.show();
        }
    }
    @FXML
    private void navigateToReservations(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/sampleres.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load AfficherResB.fxml");
            alert.show();
        }
    }

}
