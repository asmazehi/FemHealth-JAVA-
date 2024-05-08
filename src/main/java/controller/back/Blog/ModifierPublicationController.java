package controller.back.Blog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import service.Blog.PublicationService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifierPublicationController implements Initializable {

    public javafx.scene.control.Label cheminphoto;
    public javafx.scene.control.TextField TitreTF;
    public javafx.scene.control.TextField ContenuTF;
    private TableView<Publication> tableView;
    private Publication publication;
    private String photoPath;
    private int idpub;
    @FXML
    void EnregistrerPublication(ActionEvent event) throws SQLException {
        this.publication = new Publication();
        String contenu = ContenuTF.getText();
        String image = cheminphoto.getText();
        String titre = TitreTF.getText();
        Date datepub = new Date();
        if (contenu.isEmpty() || image.isEmpty() || titre.isEmpty()) {
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
            this.publication.setTitre(titre);
            this.publication.setContenu(contenu);
            this.publication.setImage(image);
            this.publication.setDatepub(datepub);
            this.publication.setId(idpub);
            PublicationService publicationService = new PublicationService();
            try {
                publicationService.update(publication);
                afficherAlerte("Publication modifiée", "La publication a été modifiée avec succès.");
                Stage stage = (Stage) ContenuTF.getScene().getWindow();
                AfficherPublicationController afficherPubController = (AfficherPublicationController) stage.getUserData();
                afficherPubController.initialize();
                ContenuTF.getScene().getWindow().hide();
            } catch (Exception e) {
                afficherAlerte("Erreur lors de la modification", "Une erreur s'est produite lors de la modification de la commande : " + e.getMessage());
            }
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Publication getData() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void AfficherPublication(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Back.Blog.AfficherPublication.fxml"));
        try {
            Parent root = fxmlLoader.load();
            AfficherPublicationController controller = fxmlLoader.getController();
            controller.setData(TitreTF.getText());
            //titreTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue AfficherPublication.fxml : " + e.getMessage());
        }
    }
    public void setData(int id) {
        this.idpub=id;
        System.out.println(id);
    }
    public void ajouter_photo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une nouvelle image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = ((FileChooser) fileChooser).showOpenDialog(new Stage());
        if (selectedFile != null) {
            String newImagePath = selectedFile.getAbsolutePath();
            cheminphoto.setText(newImagePath);
        }}
    void publicationFieldsWithData(Publication publication)
    {
    }
    public void  initializeDetails(){
        CommentaireService cs=new CommentaireService();
        try {
            Publication pub= cs.fetchPublicationById(this.idpub);
            TitreTF.setText(pub.getTitre());
            ContenuTF.setText(pub.getContenu());
            cheminphoto.setText(pub.getImage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDetails();
    }
    @FXML
    void BackAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) TitreTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
