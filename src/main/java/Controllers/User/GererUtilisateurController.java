package Controllers.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.User.Utilisateur;
import service.User.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class GererUtilisateurController {

    @FXML
    private ChoiceBox<String> choix_type;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField textField;

    @FXML
    private Button btnbloquer;
    @FXML
    private TextField idFX;


    @FXML
    private TableView<Utilisateur> tableView;

    @FXML
    private TableColumn<Utilisateur, Integer> idCol;

    @FXML
    private TableColumn<Utilisateur, String> emailCol;

    @FXML
    private TableColumn<Utilisateur, String> roleCol;

    @FXML
    private TableColumn<Utilisateur, String> activeCol;

    @FXML
    private TableColumn<Utilisateur, String> registred_atCol;


    @FXML
    private Button btnDeconnecter;


    private ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void initialize() {
        chargerUtilisateurs();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        registred_atCol.setCellValueFactory(new PropertyValueFactory<>("registred_at"));



        choix_type.getItems().addAll("Admin", "Client");
    }

    private void chargerUtilisateurs() {
        try {
            utilisateurs.setAll(utilisateurService.select());
            tableView.setItems(utilisateurs);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors du chargement des utilisateurs");
        }
    }
    private void selectionnerUtilisateur() {
        Utilisateur utilisateurSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            choix_type.setValue(utilisateurSelectionne.getRole());
            idFX.setText("ID: " + utilisateurSelectionne.getId());
        }
    }
//    public void updateUser(Utilisateur utilisateur) {
//        if (utilisateur.getMdp() != null) {
//            try {
//                utilisateurService.update(utilisateur);
//                System.out.println("Utilisateur mis à jour avec succès");
//            } catch (SQLException ex) {
//                System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + ex.getMessage());
//            }
//        } else {
//            System.out.println("Le mot de passe ne peut pas être null");
//        }
//    }



    @FXML
    private void modifierRole() {
        Utilisateur utilisateurSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne == null) {
            afficherAlerte("Sélectionnez un utilisateur");
            return;
        }

        String nouveauRole = choix_type.getValue();
        if (nouveauRole == null) {
            afficherAlerte("Sélectionnez un nouveau rôle");
            return;
        }

        utilisateurSelectionne.setRole(nouveauRole);

        // Utilisez la méthode updateUser pour mettre à jour l'utilisateur
        //updateUser(utilisateurSelectionne);
    }


    @FXML
    private void bloquerUtilisateur() {
        Utilisateur utilisateurSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne == null) {
            afficherAlerte("Sélectionnez un utilisateur");
            return;
        }

        int nouvelEtat = (utilisateurSelectionne.getActive() == 1) ? 0 : 1;
        utilisateurSelectionne.setActive(nouvelEtat);

        try {
            utilisateurService.update(utilisateurSelectionne);
            String message = nouvelEtat == 1 ? "Compte activé" : "Compte désactivé";
            afficherAlerte(message);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la mise à jour du statut du compte");
        }

        tableView.refresh();
    }
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void deconnecter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnDeconnecter.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
