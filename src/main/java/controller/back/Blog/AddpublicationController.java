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
        // Récupérer les valeurs depuis les champs de l'interface utilisateur
        String contenu = contenuTf.getText();
        String cheminPhoto = cheminphoto.getText(); // Assurez-vous que la label cheminphoto contient le chemin de la photo sélectionnée
        String titre=titreTf.getText();
        Date datepub = new Date();
        // Créer une instance de Publication avec les valeurs récupérées
        Publication nouvellePublication = new Publication();
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setImage(cheminPhoto);
        nouvellePublication.setTitre(titre);
        nouvellePublication.setDatepub(datepub);

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de publication");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir publier cette publication ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Utiliser PublicationService pour ajouter la nouvelle publication à la base de données
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
        // Filtres pour les fichiers image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Enregistrer le chemin de la photo sélectionnée
            photoPath = selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }
    }
}
