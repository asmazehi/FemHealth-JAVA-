package controller.back.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Blog.Publication;
import service.Blog.PublicationService;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AddpublicationController {

    public Label cheminphoto;
    @FXML
    private TextField contenuTf;
    @FXML
    private TextField titreTf;

    @FXML
    private Button ajouterTf;

    private String photoPath;


    @FXML
    private Button afficherTf;

    @FXML
    void ajouterPublication(ActionEvent event) {
        if (contenuTf.getText().isEmpty() || titreTf.getText().isEmpty() || cheminphoto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "entrez votre demande et donnez un exemple.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String contenu = contenuTf.getText();
        String cheminPhoto = cheminphoto.getText();  String titre=titreTf.getText();
        Date datepub = new Date();

        Publication nouvellePublication = new Publication();
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setImage(cheminPhoto);
        nouvellePublication.setTitre(titre);
        nouvellePublication.setDatepub(datepub);
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de publication");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir publier cette publication ?");

         confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                PublicationService publicationService = new PublicationService();
                try {
                    publicationService.add(nouvellePublication);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Publié");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La publication a été ajoutée avec succès !");
                    successAlert.showAndWait();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
                }
            }
        });
    }

    @FXML
    void afficherPublication(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Blog/AfficherPublication.fxml"));
        try {
            Parent root = loader.load();
            AfficherPublicationController controller = loader.getController();
            controller.setData(titreTf.getText());
            titreTf.getScene().setRoot(root);
        }catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void ajouter_photo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
       File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            photoPath = selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }
    }
}
