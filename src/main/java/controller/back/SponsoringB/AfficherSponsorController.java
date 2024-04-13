package controller.back.SponsoringB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AfficherSponsorController {
    SponsorService ss = new SponsorService();
    ObservableList<Sponsor> obs;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Sponsor, String> nomS;
    @FXML
    private TableColumn<Sponsor, String> dureeS;
    @FXML
    private TableView<Sponsor> tableView;
    @FXML
    private Label welcomeLBL;
    private static boolean ajouterSponsorPageOpen = false;
    @FXML
    void supprimerSponsor(ActionEvent event) {
        Sponsor s = tableView.getSelectionModel().getSelectedItem();
        if (s != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce sponsor ?");
            alert.setContentText("Cette action est irréversible.");

            // Customize the button text to be in French
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.getButtonTypes().forEach(buttonType -> {
                if (buttonType.getButtonData().isCancelButton()) {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Non");
                } else {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Oui");
                }
            });

            // Show the confirmation dialog and handle the result
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    ss.delete(s.getId());
                    obs.remove(s);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @FXML
    void initialize() {
        try {
            List<Sponsor> list = ss.select();
            obs = FXCollections.observableArrayList(list);

            tableView.setItems(obs);
            nomS.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dureeS.setCellValueFactory(new PropertyValueFactory<>("duree_contrat"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setData(String msg) {
        welcomeLBL.setText("Bienvenue chez FemHealth " + msg);
    }

    @FXML
    void naviguerVersAjouterSponsor(ActionEvent event) {
        try {
            // Load the AjouterSponsor page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AjouterSponsor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un Sponsor");
            stage.setOnCloseRequest(e -> {
                ajouterSponsorPageOpen = false; // Reset the flag in the AfficherSponsorController
            });
            stage.show();

            ajouterSponsorPageOpen = true;

            // Close the current stage
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue AjouterSponsor.fxml: " + e.getMessage());
        }
    }


    public static void setAjouterSponsorPageOpen(boolean open) {
        ajouterSponsorPageOpen = open;
    }
    @FXML
    void modifierSponsor(ActionEvent event) {
        Sponsor selectedSponsor = tableView.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                // Load the ModifierSponsor page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/ModifierSponsor.fxml"));
                Parent root = loader.load();
                ModifierSponsorController controller = loader.getController();
                controller.setData(selectedSponsor, (Stage) tableView.getScene().getWindow());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modifier un Sponsor");
                stage.show();

            } catch (IOException e) {
                System.err.println("Erreur lors du chargement de la vue ModifierSponsor.fxml: " + e.getMessage());
            }
        }
    }
    public void refreshData() {
        try {
            List<Sponsor> list = ss.select();
            obs = FXCollections.observableArrayList(list);

            tableView.setItems(obs);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }








}
