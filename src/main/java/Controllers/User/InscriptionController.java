package Controllers.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User.Utilisateur;
import service.User.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InscriptionController {

    @FXML
    private TextField EmailTF;

    @FXML
    private TextField mdpTF;

    @FXML
    private TextField Nom;

    @FXML
    private Button confirmerButton;
    @FXML
    private Button retourFX;

    private UtilisateurService utilisateurService;

    @FXML
    public void initialize() {
        utilisateurService = new UtilisateurService();
    }

    @FXML
    private void inscription() {
        String email = EmailTF.getText();
        String mdp = mdpTF.getText();
        String name = Nom.getText();

        // Validation de la saisie
        if (!validateEmail(email) || !validatePassword(mdp)) {
            return;
        }

        if (name.isEmpty()) {
            return;
        }

        Utilisateur existingUser = utilisateurService.authentification(email);
        if (existingUser != null) {
            System.out.println("L'adresse e-mail est déjà utilisée.");
            return;
        }

        Utilisateur user = new Utilisateur(name, email, mdp);
        try {
            utilisateurService.add(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retourFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer les erreurs liées à l'ajout de l'utilisateur à la base de données
            // ou au chargement de la page d'accueil
        }
    }

    @FXML
    private void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retourFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            showAlert("Format d'e-mail incorrect", "L'adresse e-mail doit être au format correct.");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 6) {
            showAlert("Mot de passe trop court", "Le mot de passe doit comporter au moins 6 caractères.");
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
}