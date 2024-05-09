package controller.Event;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Reservation;
import service.events.ReservationC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherResB {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Reservation, Evenement> id_evenementCol;

    @FXML
    private TableColumn<Reservation, String> mode_paiementCol;

    @FXML
    private TableColumn<Reservation, String> statut_paiementCol;

    @FXML
    private TableView<Reservation> tableview;
    private ReservationC rc = new ReservationC();
    private ObservableList<Reservation> obs;
    @FXML
    void SupprimerResB(ActionEvent event) {
        try {
            Reservation r =  tableview.getSelectionModel().getSelectedItem();
            rc.delete(r.getId());
            obs.remove(r);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        try {
            List<Reservation> list = rc.select();
            obs = FXCollections.observableArrayList(list);
            tableview.setItems(obs);

            id_evenementCol.setCellValueFactory(new PropertyValueFactory<>("id_evenement_id"));
            mode_paiementCol.setCellValueFactory(new PropertyValueFactory<>("mode_paiement"));

            // Set the statut_paiement cell value factory manually
            statut_paiementCol.setCellValueFactory(cellData -> {
                Reservation reservation = cellData.getValue();
                if (reservation != null) {
                    return new SimpleStringProperty("en attente");
                } else {
                    return new SimpleStringProperty(""); // Or any appropriate default value
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void goBackk(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            // Load the FXML file of the recent page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/BaseAdmin.fxml"));
            Parent root = loader.load();

            // Set the recent page scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the recent page
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions while loading the FXML file
        }
    }

    @FXML
    void ModifierResB(ActionEvent event) {
        try {
            Reservation selectedReservation = tableview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/ModifierResB.fxml"));
                Parent root = loader.load();

                ModifierResB controller = loader.getController();
                controller.initData(selectedReservation);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune réservation sélectionnée");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ModifierResBF(ActionEvent event) {
        try {
            Reservation selectedReservation = tableview.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/ModifResF.fxml"));
                Parent root = loader.load();

                ModifierResB controller = loader.getController();
                controller.initData(selectedReservation);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune réservation sélectionnée");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goBack(ActionEvent event) {
        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            // Load the FXML file of the recent page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageEventF.fxml"));
            Parent root = loader.load();

            // Set the recent page scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the recent page
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions while loading the FXML file
        }
    }

}



