package controller.front.Blog;

import controller.back.Blog.AfficherPublicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
public class UpdateComment {

    @FXML
    private TextField descriptionFld;
    private Commentaire commentaire;
    private int idComnnt;

    @FXML
    void saveCommentaire(ActionEvent event) {
        this.commentaire= new Commentaire();
        String description = descriptionFld.getText();
        System.out.println("hrrrrrll"+this.idComnnt);
        Date datecomnt = new Date();
        if (description.isEmpty() ) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Champs vides");
            errorAlert.setHeaderText("Veuillez remplir tous les champs.");
            errorAlert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Voulez-vous enregistrer la publication?");
        confirmationAlert.setContentText("Cette action ne peut pas être annulée.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.commentaire.setDescription(description);
            this.commentaire.setDatecomnt(datecomnt);
            this.commentaire.setId(this.idComnnt);
            CommentaireService cs=new CommentaireService();
            try {
                cs.update(commentaire);
                afficherAlerte("Publication modifiée", "La publication a été modifiée avec succès.");
                Stage stage = (Stage) descriptionFld.getScene().getWindow();
                UserComments detailsController = (UserComments) stage.getUserData();
                detailsController.initialize();
                descriptionFld.getScene().getWindow().hide();
            } catch (SQLException e) {
                afficherAlerte("Erreur lors de la modification", "Une erreur s'est produite lors de la modification de la commande : " + e.getMessage());
            }
        }
    }

    private void afficherAlerte(String description, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(description);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setData(int id) {
        this.idComnnt=id;
        System.out.println("id de set data"+id);
        System.out.println("id de set data"+this.idComnnt);
        System.out.println(id);
    }
    public void initializeDetailsCom() {
        try {
            System.out.println("id de set data de initializeDetailsCom"+this.idComnnt);

            CommentaireService cs = new CommentaireService();
            List<Commentaire> commentaires = cs.fetchCommentaireByID(this.idComnnt);
            System.out.println(this.idComnnt + "pleaseeeee");

            if (!commentaires.isEmpty()) {
                Commentaire com = commentaires.get(idComnnt);
                descriptionFld.setText(com.getDescription()+"is updated");
                System.out.println("aaa"+com.getDescription());
            } else {
                descriptionFld.setText("Aucun commentaire trouvé pour cet utilisateur.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDetailsCom();
    }
    @FXML
    void BackTo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Blog/UserComments.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) descriptionFld.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
