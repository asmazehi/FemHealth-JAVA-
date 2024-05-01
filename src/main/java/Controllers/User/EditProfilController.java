package Controllers.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import model.User.Utilisateur;
import service.User.UtilisateurService;
import utils.PasswordUtils;

public class EditProfilController {
    @FXML
    private TextField EmailTF;

    @FXML
    private CheckBox changemdp;

    @FXML
    private PasswordField mdpAC_TF;

    @FXML
    private PasswordField mdpNV_TF;

    @FXML
    private PasswordField mdpC_TF;

    @FXML
    private Button ConfirmationTF;
    @FXML
    private Button retour_TF;

    private UtilisateurService utilisateurService;

    @FXML
    private void initialize() {
        utilisateurService = new UtilisateurService();

    }

    private Utilisateur user;

    public void setData(Utilisateur user){

        this.user = user;
        EmailTF.setText(user.getEmail());

    }

    @FXML
    private void Modifiermdp() {
        if (changemdp.isSelected()) {
            mdpAC_TF.setVisible(true);
            mdpNV_TF.setVisible(true);
            mdpC_TF.setVisible(true);
        } else {
            mdpAC_TF.setVisible(false);
            mdpNV_TF.setVisible(false);
            mdpC_TF.setVisible(false);
        }
    }

    @FXML
    private void ModifierInfo() {

        String email = EmailTF.getText();
        String motDePasseActuel = mdpAC_TF.getText();
        String nouveauMotDePasse = mdpNV_TF.getText();
        String confirmationMotDePasse = mdpC_TF.getText();

        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            showAlert("Confirmation invalide", "La confirmation du mot de passe ne correspond pas au nouveau mot de passe.");
            return;
        }

        System.out.println(EmailTF);

        if(email.equals(user.getEmail()))
        {


            if(PasswordUtils.verifyPassword(motDePasseActuel,user.getMdp()))
        {


            if(nouveauMotDePasse.contains(confirmationMotDePasse))
        {

            user.setMdp(PasswordUtils.hashPasswrd(nouveauMotDePasse));
            try {
                utilisateurService.update(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePageClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ConfirmationTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}else{
                System.out.println("wrong confirmation");

            }
        }else{
                System.out.println("wrong password");
            }
        }else{
            System.out.println("wrong email");
        }
    }

    private boolean validerSaisie(String email, String motDePasseActuel, String nouveauMotDePasse, String confirmationMotDePasse) {
        if (email.isEmpty()) {
            showAlert("Champ manquant", "Veuillez saisir votre adresse e-mail.");
            return false;
        }

        if (!motDePasseActuel.equals(user.getMdp())) {
            showAlert("Mot de passe incorrect", "Le mot de passe actuel est incorrect.");
            return false;
        }
        if (!utilisateurService.validerMotDePasse(email, motDePasseActuel)) {
            showAlert("Mot de passe incorrect", "Le mot de passe actuel est incorrect.");
            return false;
        }


        if (nouveauMotDePasse.length() < 6) {
            showAlert("Format incorrect", "Le nouveau mot de passe doit comporter au moins 6 caractères.");
            return false;
        }


        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            showAlert("Confirmation invalide", "La confirmation du mot de passe ne correspond pas au nouveau mot de passe.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Authentification.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour_TF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

