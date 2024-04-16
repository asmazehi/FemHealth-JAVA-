package controller.back.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Blog.Publication;
import service.Blog.PublicationService;

public class AfficherPublicationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Publication, String> titreCol;

    @FXML
    private Label welcomeLBL;

    @FXML
    private TableColumn<Publication, String> contenuCol;

    @FXML
    private TableColumn<Publication, Date> dateCol;

    @FXML
    private TableColumn<Publication, String> imageCol;

    @FXML
    private TableView<Publication> tableView;
    PublicationService ps = new PublicationService();
    ObservableList<Publication> obs ;
    @FXML
    void supprimerPublication(ActionEvent event) throws SQLException {

        try
        {
            Publication p =tableView.getSelectionModel().getSelectedItem();
            ps.delete(p.getId());
            System.out.println(p.getId());
            obs.remove(p);
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
    void modifierPublication(ActionEvent event) {
        PublicationService publicationService = new PublicationService();
        Publication publication = tableView.getSelectionModel().getSelectedItem(); // Récupérer la publication sélectionnée dans le TableView
        if (publication == null) {
            // Aucune publication sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une publication à modifier.");
            alert.showAndWait();
            return;
        }
        // Modifier les champs de la publication avec les valeurs saisies dans les champs de texte
        publication.setTitre(titreCol.getText());
        publication.setContenu(contenuCol.getText());
        publication.setImage(imageCol.getText());

        try {
            publicationService.update(publication); // Appeler la méthode de service pour modifier la publication
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Publication modifiée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            // En cas d'erreur lors de la modification, afficher une boîte de dialogue d'erreur avec le message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la modification de la publication : " + e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void initialize() {
        try {
            List<Publication> list = ps.select();
            obs = FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("datepub"));
            contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void setData(String msg){
        welcomeLBL.setText("Welcome" + msg);
    }
    @FXML
    public void modifierpublication(ActionEvent actionEvent) {
        Publication selectedPublication = tableView.getSelectionModel().getSelectedItem();
        if (selectedPublication == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune publication sélectionnée");
            alert.setContentText("Veuillez sélectionner une publication à modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la publication");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette publication ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Blog/updatePublication.fxml"));
            try {
                Parent root = loader.load();
                dialog.getDialogPane().setContent(root);
                ModifierPublicationController modifierPubController = loader.getController();
                modifierPubController.setData(selectedPublication.getId());

                // Set the userData property
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.setUserData(this); // Assuming "this" refers to AfficherPubController

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> dialogResult = dialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                initialize();
            }
        }
    }

    public void addpublication(ActionEvent actionEvent) {

    }
}
