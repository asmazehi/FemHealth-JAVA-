package controller.back.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.front.Blog.DetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
        Publication publication = tableView.getSelectionModel().getSelectedItem(); // R?cup?rer la publication s?lectionn?e dans le TableView
        if (publication == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez s?lectionner une publication ? modifier.");
            alert.showAndWait();
            return;
        }
        publication.setTitre(titreCol.getText());
        publication.setContenu(contenuCol.getText());
        publication.setImage(imageCol.getText());

        try {
            publicationService.update(publication); // Appeler la m?thode de service pour modifier la publication
            // Afficher une bo?te de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succ?s");
            alert.setContentText("Publication modifi?e avec succ?s");
            alert.showAndWait();
        } catch (SQLException e) {
            // En cas d'erreur lors de la modification, afficher une bo?te de dialogue d'erreur avec le message d'erreur
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
            alert.setTitle("Aucune publication s?lectionn?e");
            alert.setContentText("Veuillez s?lectionner une publication ? modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la publication");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("?tes-vous s?r de vouloir modifier cette publication ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Blog/updatePublication.fxml"));
            try {


                Parent root = loader.load();

                controller.back.Blog.ModifierPublicationController controller = loader.getController();


                controller.setData(selectedPublication.getId());
                controller.initializeDetails();


                Scene scene = new Scene(root);


                Stage stage = new Stage();
                stage.setScene(scene);


                stage.initModality(Modality.APPLICATION_MODAL);

                stage.showAndWait();


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
    @FXML
    public void addpublication(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Blog/Addpublication.fxml"));
            Parent root = loader.load();

            // Cr?er une nouvelle sc?ne
            Scene scene = new Scene(root);

            // Obtenir la fen?tre principale actuelle
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // D?finir la nouvelle sc?ne dans la fen?tre principale
            stage.setScene(scene);

            // Afficher la nouvelle sc?ne
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // G?rer l'exception de chargement du fichier FXML
        }
    }

    }

