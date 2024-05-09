package controller.Event;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;

import java.io.File;
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

    @FXML
    void ajouterEventB(ActionEvent actionEvent) {
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
            // Handle loading error
            showErrorAlert("Failed to load AjouterFXML.fxml");
        }

    }

    @FXML
    void supprimerEventB(ActionEvent event) {
        try {
            Evenement e = tableview.getSelectionModel().getSelectedItem();
            ec.delete(e.getId());
            obs.remove(e);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierEventB(ActionEvent event) {
        Evenement evenement = tableview.getSelectionModel().getSelectedItem();

        if (evenement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/ModifierEventB.fxml"));
                Parent root = loader.load();

                ModifierEventB controller = loader.getController();
                controller.initData(evenement);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle loading error
                showErrorAlert("Failed to load ModifierEventB.fxml");
            }
        } else {
            // Show a warning if no event is selected
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un événement à modifier");
        }
    }

    @FXML
    void initialize() {
        try {
            List<Evenement> list = ec.select();
            obs = FXCollections.observableArrayList(list);
            tableview.setItems(obs);

            type_idCol.setCellValueFactory(cellData -> {
                Type type = cellData.getValue().getType_id();
                return new ReadOnlyObjectWrapper<>(type);
            });

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            localisationCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
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

            imageCol.setCellFactory(column -> new TableCell<Evenement, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);

                    if (empty || imagePath == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        File file = new File(imagePath);
                        if (file.exists()) {
                            Image image = new Image(file.toURI().toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            setGraphic(imageView);
                            setText(null);
                        } else {
                            setText("Image not found");
                        }
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private void openReservationPage(int eventId) {
        // Get a reference to the current stage
        Stage currentStage = (Stage) tableview.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/back.Event/AjouterResB.fxml"));
            Parent root = loader.load();

            AjouterResB controller = loader.getController();
            controller.initData(eventId);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // Close the current stage before opening the new one
            currentStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle loading error
            showErrorAlert("Failed to load AjouterResB.fxml");
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
            // Handle loading error
            showErrorAlert("Failed to load AfficherResB.fxml");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.show();
    }

    private void showErrorAlert(String contentText) {
        showAlert(Alert.AlertType.ERROR, "Error", contentText);
    }

    @FXML
    private void showStatistics(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/statEvent.fxml"));
            Parent root = loader.load();

            StatController statController = loader.getController();
            statController.initialize(); // Initialize the statistics in the controller

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }
    }
}

