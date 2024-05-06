package controller.User;

import controller.back.Blog.AfficherCommentaire;
import controller.back.Blog.AfficherPublicationController;
import controller.front.Blog.DetailsController;
import controller.front.Blog.FXMLDocumentController;
import controller.front.Blog.UserComments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import model.User.Utilisateur;
import service.User.UtilisateurService;
import utils.PasswordUtils;
import javafx.scene.control.Hyperlink;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Set;

import javafx.scene.control.PasswordField;


public class AuthentificationController {

    @FXML
    private TextField EmailTF;

    @FXML
    private PasswordField mdp_TF ;

    @FXML
    private Button seConnecterTF;
    @FXML
    private Button retour_TF;


    @FXML
    private Hyperlink mdpOublieTF;

    private UtilisateurService utilisateurService;

    @FXML
    private void initialize() {
        utilisateurService = new UtilisateurService();
        seConnecterTF.disableProperty().bind(EmailTF.textProperty().isEmpty()
                .or(mdp_TF.textProperty().isEmpty()));

    }

    @FXML
    void kiker(ActionEvent event) {
        String email = EmailTF.getText();
        String motDePasse = mdp_TF.getText();

        if (!isValidEmail(email)) {
            showAlert("Adresse email invalide !");
            return;
        }

        Utilisateur utilisateur = utilisateurService.authentification(email);

        if (utilisateur != null && PasswordUtils.verifyPassword(motDePasse, utilisateur.getMdp())) {

            if (utilisateur.getRole().equals("ROLE_ADMIN")) {

                System.out.println("Redirecting to BaseAdmin");
                try {
                    redirectToBaseAdmin();
                } catch (Exception e) {
                    showAlert("Erreur lors de la redirection vers l'interface administrateur.");
                    e.printStackTrace();
                }
            } else if (utilisateur.getRole().equals("ROLE_CLIENT")) {
                System.out.println("Redirecting to HomePageClient");
                redirectToHomePageClient();
            }
        } else {
            showAlert("Email ou mot de passe incorrect !");
        }
    }
    @FXML
    private void seConnecter() throws IOException {
        String email = EmailTF.getText();
        String motDePasse = mdp_TF.getText();

        if (!isValidEmail(email)) {
            showAlert("Adresse email invalide !");
            return;
        }

        Utilisateur utilisateur = utilisateurService.authentification(email);

        System.out.println(utilisateurService.user);
        if (utilisateur != null && PasswordUtils.verifyPassword(motDePasse, utilisateur.getMdp())) {

            SetData(utilisateur);

            if (utilisateur.getRole().equals("ROLE_ADMIN")) {

                System.out.println("Redirecting to BaseAdmin");
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
                    Parent root = loader.load();
                    AfficherPublicationController controller = loader.getController();


                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    redirectToHomePageClient();
                } catch (Exception e) {
                    showAlert("Erreur lors de la redirection vers l'interface administrateur.");
                    e.printStackTrace();
                }
            } else if (utilisateur.getRole().contains("ROLE_CLIENT")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Blog/carCard.fxml"));
                Parent root = loader.load();
              loader.getController();


                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                redirectToHomePageClient();
            }
        } else {
            showAlert("Email ou mot de passe incorrect !");
        }
    }

    private Utilisateur CurrentUser;
    public void SetData(Utilisateur user){
        this.CurrentUser = user;
    }

    private void redirectToBaseAdmin() {
        System.out.println("Redirecting to BaseAdmin");
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

    private void redirectToHomePageClient() {
        System.out.println("Redirecting to HomePageClient");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePageClient.fxml"));
            Parent root = loader.load();

            HomePageClientController controller = loader.getController();
            System.out.println(CurrentUser);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ResetPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) mdpOublieTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
}
