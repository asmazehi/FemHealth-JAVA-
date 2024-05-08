package controller.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import service.User.UtilisateurService;
import utils.EmailUtils;
import java.util.Random;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ResetPasswordController {
    @FXML
    private PasswordField mdp1TF;

    @FXML
    private PasswordField mdp2TF;

    @FXML
    private Button confirmerTF;

    @FXML
    private Button retour_TF;

    @FXML
    private Label emailLabel;

    private String email;
    private String sentCode; // Vous devez initialiser cette variable avec le code envoyé

    private UtilisateurService utilisateurService;

    public void setEmail(String email) {
        this.email = email;
        emailLabel.setText(email); // Afficher l'e-mail dans le label
    }

    public void initialize() {
        utilisateurService = new UtilisateurService();
    }


    @FXML
    private void Confirmer() {
        String password = mdp1TF.getText();
        String confirmPassword = mdp2TF.getText();
        String verificationCode = generateVerificationCode(); // Générer le code de vérification

        try {
            sendResetPasswordEmail(email, verificationCode); // Envoi de l'email avec le code de vérification
        } catch (MessagingException e) {
            e.printStackTrace();
            afficherAlerte("Une erreur s'est produite lors de l'envoi de l'email.");
            return;
        }

        // Demander à l'utilisateur de saisir le code de vérification
        String enteredVerificationCode = askVerificationCode();

        // Vérifier si le code de vérification saisi correspond au code envoyé
        if (enteredVerificationCode != null && verifyCode(enteredVerificationCode, verificationCode)) {
            // Si les codes correspondent, vérifier les mots de passe et effectuer la mise à jour si nécessaire
            if (password.equals(confirmPassword)) {
                try {
                    utilisateurService.updatePassword(email, password);
                    afficherAlerte("Votre mot de passe a été modifié avec succès.");
                    retour();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    afficherAlerte("Une erreur s'est produite lors de la mise à jour du mot de passe.");
                }
            } else {
                afficherAlerte("Les mots de passe ne correspondent pas.");
            }
        } else {
            afficherAlerte("Code de vérification incorrect. Veuillez vérifier le code ou saisir votre vrai e-mail.");
        }
    }

    private String askVerificationCode() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Code de vérification");
        dialog.setHeaderText(null);
        dialog.setContentText("Veuillez entrer le code de vérification envoyé à votre e-mail :");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private boolean verifyCode(String inputCode, String sentCode) {
        return inputCode.equals(sentCode);
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
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
    private void sendResetPasswordEmail(String email, String verificationCode) throws MessagingException {

        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String username = "chaimatlili62@gmail.com";
        String password = "bxra lvjy ajes ajqs";


        String subject = "Réinitialisation de votre mot de passe";
        String body = "Bonjour,\n\n"
//                + "Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant :\n"
//                + "http://votre-site.com/reset-password\n\n"

                + "Pour réinitialiser votre mot de passe, veuillez utiliser le code de vérification suivant :\n"
                + verificationCode + "\n\n"
                + "Cordialement,\n"
                + "Votre équipe";

        EmailUtils.sendEmail(smtpHost, smtpPort, username, password, email, subject, body);
    }
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Générer un chiffre aléatoire entre 0 et 9
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();

    }
}