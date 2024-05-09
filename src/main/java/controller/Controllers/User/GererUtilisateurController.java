package controller.Controllers.User;
import javafx.event.ActionEvent;
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
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;
import javafx.scene.control.Button;
import utils.EmailUtils;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

import javax.mail.MessagingException;
import java.util.TreeSet;


public class GererUtilisateurController {

    public Hyperlink statistiquesFX;
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
    private Button retour_TF;
    @FXML
    private TextField searchFX;
    @FXML
    private DatePicker datePickerFX;

    @FXML
    private Button searchButtonFX;

    private FilteredList<Utilisateur> filteredUtilisateurs;


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
    private TableColumn<Utilisateur, String> passwordCol;

    @FXML
    private TableColumn<Utilisateur, String> registered_atCol;
    @FXML
    private Button btnDeconnecter;


    private ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
    private UtilisateurService utilisateurService = new UtilisateurService();


    @FXML
    private void initialize() {
        chargerUtilisateurs();

        // Configuration des cellules de la TableView
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        registered_atCol.setCellValueFactory(new PropertyValueFactory<>("registered_at"));


        choix_type.getItems().addAll("[\"ROLE_CLIENT\"]", "[\"ROLE_ADMIN\"]");


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                choix_type.setValue(newSelection.getRole());
            }
        });


        filteredUtilisateurs = new FilteredList<>(utilisateurs, p -> true);
        tableView.setItems(filteredUtilisateurs);
        searchFX.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUtilisateurs.setPredicate(utilisateur -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }


                String lowerCaseFilter = newValue.toLowerCase();


                if (utilisateur.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    Date registeredAt = utilisateur.getRegistred_at();
                    if (registeredAt != null && registeredAt.toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }

                return false;
            });
        });
    }



    private void chargerUtilisateurs() {
        utilisateurs.clear(); // Vide la liste avant de l'ajouter à nouveau

        try {
            List<Utilisateur> nouveauxUtilisateurs = utilisateurService.select();
            int nouveaux = nouveauxUtilisateurs.size();
            StringBuilder nouveauxUtilisateursMessage = new StringBuilder();
            for (Utilisateur utilisateur : nouveauxUtilisateurs) {
                utilisateurs.add(utilisateur); // Ajoute l'utilisateur au TreeSet
                nouveauxUtilisateursMessage.append(utilisateur.getEmail()).append(", ");
            }

            if (nouveaux > 0) {
                afficherNotification(nouveaux + " nouveaux utilisateur(s) ajouté(s) : " + nouveauxUtilisateursMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors du chargement des utilisateurs");
        }
    }




    private void afficherNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nouveaux Utilisateurs");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        try {

            utilisateurService.update(utilisateurSelectionne);
            afficherAlerte("Rôle de l'utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la mise à jour du rôle de l'utilisateur");
        }

        tableView.refresh(); // Rafraîchit la table pour refléter les modifications
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
            utilisateurService.updateActivation(utilisateurSelectionne.getId(), nouvelEtat);
            String message = nouvelEtat == 1 ? "Compte activé" : "Compte désactivé";
            afficherAlerte(message);

            String sujet = "Modification de l'état de votre compte";
            String contenu = "Cher utilisateur,\n\n"
                    + "Votre compte a été " + (nouvelEtat == 1 ? "activé." : "désactivé.") + "\n\n"
                    + "Cordialement,\n"
                    + "Votre équipe.";
            String smtpHost = "smtp.gmail.com";
            String smtpPort = "587";
            String username = "chaimatlili62@gmail.com"; // Remplacez par votre nom d'utilisateur SMTP
            String password = "bxra lvjy ajes ajqs"; // Remplacez par votre mot de passe SMTP
            String emailUtilisateur = utilisateurSelectionne.getEmail();

            try {
                EmailUtils.sendEmail(smtpHost, smtpPort, username, password, emailUtilisateur, sujet, contenu);
            } catch (MessagingException e) {
                e.printStackTrace();
                afficherAlerte("Erreur lors de l'envoi de l'email");
            }

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

    public void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/BaseAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour_TF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chercher(ActionEvent actionEvent) {
    }
    @FXML
    private void chercherParDate() {
        LocalDate selectedDate = datePickerFX.getValue();
        if (selectedDate != null) {
            filteredUtilisateurs.setPredicate(utilisateur -> {
                Date registeredAt = utilisateur.getRegistred_at();
                if (registeredAt != null) {
                    LocalDate registeredDate = ((java.sql.Date) registeredAt).toLocalDate();
                    return registeredDate.equals(selectedDate);
                } else {
                    return false; // Si la date d'inscription est nulle, ne pas inclure cet utilisateur
                }
            });

            // Utiliser la date pour effectuer la recherche
            // Implementer la logique de recherche par date ici
        } else {
            afficherAlerte("Sélectionnez une date");
        }
    }

    public void statistiques() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Statistiques.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) statistiquesFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}