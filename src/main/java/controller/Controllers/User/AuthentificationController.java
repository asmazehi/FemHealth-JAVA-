
        package controller.Controllers.User;

//import com.sun.javafx.webkit.WebConsoleListener;
import controller.Event.AffichageEventF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User.Utilisateur;
import service.User.UtilisateurService;
import utils.PasswordUtils;
import java.io.IOException;
import java.util.List;

//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;

public class AuthentificationController {

    public static boolean isRobotVerified;
    @FXML
    private TextField EmailTF;

    @FXML
    private PasswordField mdp_TF ;

    @FXML
    private Button seConnecterTF;
    @FXML
    private Button inscireTF;

    @FXML
    private Button retour_TF;

    @FXML
    private Hyperlink mdpOublieTF;

    @FXML
    private CheckBox RobotCheckBox;

    private UtilisateurService utilisateurService;
//    private boolean isRobotVerified = false;
//    @FXML
//    private WebView webView;


    @FXML
    private void initialize() {
        utilisateurService = new UtilisateurService();
        seConnecterTF.disableProperty().bind(EmailTF.textProperty().isEmpty()
                .or(mdp_TF.textProperty().isEmpty()));
     /*   WebEngine webEngine = webView.getEngine();
        WebConsoleListener.setDefaultListener(
                (webView, message, lineNumber, sourceId)-> System.out.println("Console: [" + sourceId + ":" + lineNumber + "] " + message)
        );//        String SITE_KEY = "6Le1VIMpAAAAAOLnzO7R8vYo3-ySZlo0w6WxLRj2";
        webEngine.loadContent(" <script src=\"https://www.google.com/recaptcha/api.js\"></script> <script>\n" +
                "   function onSubmit(token) {\n" +
                "     document.getElementById(\"demo-form\").submit();\n" +
                "   }\n" +
                " </script><button class=\"g-recaptcha\" \n" +
                "        data-sitekey=\"6Le1VIMpAAAAAOLnzO7R8vYo3-ySZlo0w6WxLRj2\" \n" +
                "        data-callback='onSubmit' \n" +
                "        data-action='submit'>Submit</button>","text/html");*/

    }

    @FXML
    void kiker(ActionEvent event) {

    }

    @FXML
    private void seConnecter() {
        if (!isRobotVerified && !RobotCheckBox.isSelected()) {
            showAlert("Veuillez vérifier que vous n'êtes pas un robot.");
            return;
        }
        if (isRobotVerified) {
            // Redirection directe vers la page d'authentification
            redirectToAuthentification();
        } else if (RobotCheckBox.isSelected()) {
            // Vérifier le reCAPTCHA
            redirectToImNotRobot();
        } else {
            String email = EmailTF.getText();
            String motDePasse = mdp_TF.getText();

            if (!isValidEmail(email)) {
                showAlert("Adresse email invalide !");
                return;
            }

            Utilisateur utilisateur = utilisateurService.authentification(email);

            if (utilisateur != null) {
                if (utilisateur.getActive() == 0) {
                    showAlert("Votre compte a été désactivé.");
                    return;
                }

                if (PasswordUtils.verifyPassword(motDePasse, utilisateur.getMdp())) {
                    SetData(utilisateur);

                    if (utilisateur.getRole().contains("[\"ROLE_ADMIN\"]")) {
                        redirectToBaseAdmin();
                    } else if (utilisateur.getRole().contains("[\"ROLE_CLIENT\"]")) {
                        redirectToAffichageEventTo();
                    }
                } else {
                    showAlert("Email ou mot de passe incorrect !");
                }
            } else {
                showAlert("Email incorrect !");
            }
        }
    }

    private void redirectToAuthentification() {
        String email = EmailTF.getText();
        String motDePasse = mdp_TF.getText();

        if (!isValidEmail(email)) {
            showAlert("Adresse email invalide !");
            return;
        }

        Utilisateur utilisateur = utilisateurService.authentification(email);

        if (utilisateur != null) {
            if (utilisateur.getActive() == 0) {
                showAlert("Votre compte a été désactivé.");
                return;
            }

            if (PasswordUtils.verifyPassword(motDePasse, utilisateur.getMdp())) {
                SetData(utilisateur);

                if (utilisateur.getRole().contains("[\"ROLE_ADMIN\"]")) {
                    redirectToBaseAdmin();
                } else if (utilisateur.getRole().contains("[\"ROLE_CLIENT\"]")) {
                    redirectToAffichageEventTo();
                }
            } else {
                showAlert("Email ou mot de passe incorrect !");
            }
        } else {
            showAlert("Email incorrect !");
        }

    }


    private void redirectToImNotRobot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ImNotRobotFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Réinitialiser la variable isRobotVerified lorsque vous revenez à ImNotRobotFXML.fxml
            stage.setOnHidden(event -> {
                isRobotVerified = false;
                // Vous pouvez également réinitialiser les champs de texte et d'autres éléments de l'interface utilisateur ici
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCaptchaValid(List<ImageView> images) {
        for (ImageView imageView : images) {
            if (imageView.getImage() != null) {
                return false; // Si une image est sélectionnée, le reCAPTCHA n'est pas valide
            }
        }
        return true; // Si aucune image n'est sélectionnée, le reCAPTCHA est valide
    }

    private Utilisateur CurrentUser;

    public void SetData(Utilisateur user) {
        this.CurrentUser = user;
    }

    private void redirectToBaseAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/BaseAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) seConnecterTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToAffichageEventTo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageEventF.fxml"));
            Parent root = loader.load();

            AffichageEventF controller = loader.getController();
            controller.SetData(CurrentUser);

            Stage stage = (Stage) seConnecterTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(emailPattern);
    }

    @FXML
    private void motDePasseOublie() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ChangerMotDePasse.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mdpOublieTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void retour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour_TF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Inscription.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) inscireTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}