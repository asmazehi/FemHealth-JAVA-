package controller.back.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Blog.Publication;
import service.Blog.PublicationService;
import javafx.stage.FileChooser;
 import java.io.File;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class AddpublicationController {

    public Label cheminphoto;
    @FXML
    private TextField contenuTf;
    @FXML
    private TextField titreTf;
 private File selectedFile;
    @FXML
    private Button ajouterTf;
  private String path_image ;
        String pathimage;
    private String photoPath;


    @FXML
    private Button afficherTf;

    @FXML
    void ajouterPublication(ActionEvent event) {
        if (contenuTf.getText().isEmpty() || titreTf.getText().isEmpty() ) {
            JOptionPane.showMessageDialog(null, "entrez votre demande et donnez un exemple.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String contenu = contenuTf.getText();
      String titre=titreTf.getText();
        Date datepub = new Date();

        Publication nouvellePublication = new Publication();
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setImage(path_image);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
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



    private String saveImage(File file) {
        // Specify the target directory to save the images
        String targetDirectory = "C:/xampp8/htdocs/femHealthfinal/public/uploads/";
        System.out.println(targetDirectory);
        // Ensure the target directory exists, create it if not
        File directory = new File(targetDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Créer le répertoire et ses parents s'ils n'existent pas
        }

        // Generate a unique name for the image file to avoid overwriting
        String imageName = System.currentTimeMillis() + "_" + file.getName();
        this.path_image = imageName;
        this.pathimage = targetDirectory + imageName;
        System.out.println(path_image);
        try {
            // Copier le fichier dans le répertoire cible
            java.nio.file.Files.copy(file.toPath(), Paths.get(pathimage), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions ici selon votre logique d'application
        }

        return path_image;
    }
    @FXML
    void ajouter_photo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Filtrer uniquement les fichiers image
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Afficher la boîte de dialogue pour sélectionner un fichier
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Appel à la méthode saveImage() pour sauvegarder l'image et récupérer le chemin
            String imagePath = saveImage(selectedFile);

            // Si l'enregistrement de l'image est réussi, vous pouvez maintenant insérer le chemin de l'image dans la base de données
            if (imagePath != null && !imagePath.isEmpty()) {
                // Insérer les données dans la base de données directement ici
                try {
                    // Connexion à la base de données
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/femHealth","root", "");

                    // Création de la déclaration
                    Statement statement = connection.createStatement();

                    // Insérer les données dans la base de données
                    String sql = "INSERT INTO publication (image) VALUES ('" + imagePath + "')";
                    statement.executeUpdate(sql);

                    // Fermeture des ressources
                    statement.close();
                    connection.close();

                    // Afficher un message de succès ou effectuer d'autres actions nécessaires
                    System.out.println("Image insérée avec succès dans la base de données.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'exception si l'insertion échoue
                }
            } else {
                // Gérer le cas où le chemin de l'image est vide ou nul
                System.out.println("Le chemin de l'image est vide ou nul.");
            }
        } else {
            // Gérer le cas où aucun fichier n'est sélectionné
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    @FXML
    void BackAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) titreTf.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
