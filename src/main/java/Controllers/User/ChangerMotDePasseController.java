package Controllers.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User.Utilisateur;
import service.User.UtilisateurService;
import utils.EmailUtils;

import javax.mail.MessagingException;

public class ChangerMotDePasseController {

    @FXML
    private TextField emailTF;

    @FXML
    private Button confirmerTF;

    @FXML
    private Button retour_TF;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void confirmer() {
        String email = emailTF.getText();
        if (email.isEmpty()) {
            afficherAlerte("Veuillez entrer votre adresse e-mail.");
            return;
        }

        // Vérification de l'existence de l'email dans la base de données
        if (!emailExiste(email)) {
            afficherAlerte("Cet e-mail n'est pas enregistré dans notre base de données.");
            return;
        }

        try {
            sendResetPasswordEmail(email);
            afficherAlerte("Un e-mail de réinitialisation a été envoyé à votre adresse.");
        } catch (MessagingException e) {
            afficherAlerte("Une erreur s'est produite lors de l'envoi de l'e-mail de réinitialisation. Veuillez réessayer.");
            e.printStackTrace();
        }
    }

    @FXML
    private void retour() {
        // Code pour retourner à l'interface précédente
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean emailExiste(String email) {
        return utilisateurService.emailExiste(email);
    }

    private void sendResetPasswordEmail(String email) throws MessagingException {
        // Remplacez les valeurs suivantes par vos propres informations SMTP
        String smtpHost = "smtp.example.com";
        String smtpPort = "587";
        String username = "your-email@example.com";
        String password = "your-email-password";

        // Vous pouvez personnaliser le contenu de l'e-mail comme vous le souhaitez
        String subject = "Réinitialisation de votre mot de passe";
        String body = "Bonjour,\n\n"
                + "Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien suivant :\n"
                + "http://votre-site.com/reset-password\n\n"
                + "Cordialement,\n"
                + "Votre équipe";

        EmailUtils.sendEmail(smtpHost, smtpPort, username, password, email, subject, body);
    }
}
