package controller.back.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.front.Blog.DetailsController;
import javafx.beans.property.SimpleStringProperty;
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
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import service.Blog.PublicationService;

public class AfficherPublicationController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField recherche;
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
    @FXML
    private TableColumn<Publication,String> idCommentaire;
    @FXML
    private TextField searchTextField;
    private ObservableList<Publication> allPublications;
    private ObservableList<Publication> filteredPublications;
    PublicationService ps = new PublicationService();
    CommentaireService cs = new CommentaireService();
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
        Publication publication = tableView.getSelectionModel().getSelectedItem();
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
            publicationService.update(publication);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succ?s");
            alert.setContentText("Publication modifi?e avec succ?s");
            alert.showAndWait();
        } catch (SQLException e) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la modification de la publication : " + e.getMessage());
            alert.showAndWait();
        }
    }@FXML
    void initialize() {
        try {
            recherche.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    updatePublicationsByTitreAndContenu(newValue,newValue);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
            List<Publication> list = ps.select();
            obs = FXCollections.observableArrayList(list);
            allPublications = FXCollections.observableArrayList(list);
            filteredPublications = FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("datepub"));
            contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
            idCommentaire.setCellValueFactory(cellData -> {
                Publication publication1 = cellData.getValue();
                List<Commentaire> commentaires = null;
                try {
                    commentaires = cs.fetchCommentaireByPublicationID(publication1.getId());
                    for (Commentaire commentaire : commentaires) {
                        StringBuilder commentairesText = new StringBuilder();
                        commentairesText.append(commentaire.getDescription());
                        if (commentaires != null) {
                            return new SimpleStringProperty(commentairesText.toString());
                        } else {
                            return new SimpleStringProperty("");
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
return  null;

            });

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
            alert.setContentText("Veuillez selectionner une publication ? modifier.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/updatePublication.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/Addpublication.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void searchPublication() {
        String searchText = searchTextField.getText().toLowerCase().trim();
        filteredPublications.clear();
        for (Publication publication : allPublications) {
            if (publication.getContenu().toLowerCase().contains(searchText)) {
                filteredPublications.add(publication);
            }
        }
    }
    @FXML
    void listeCommentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherCommentaire.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private class CommentaireTableCell extends TableCell<Publication, String> {
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                Publication publication = getTableView().getItems().get(getIndex()); // Récupérer la publication
                int publicationId = publication.getId(); // ID de la publication
                try {
                    List<Commentaire> commentaires = cs.fetchCommentaireByPublicationID(publicationId);
                    StringBuilder commentairesText = new StringBuilder();
                    for (Commentaire commentaire : commentaires) {
                        commentairesText.append(commentaire.getDescription()).append("\n");
                    }
                    setText(commentairesText.toString());
                } catch (SQLException e) {
                    setText("Erreur lors du chargement des commentaires");
                    e.printStackTrace();
                }
            }
        }
    }
    private void updatePublicationsByTitreAndContenu(String titre, String contenu) throws SQLException {
        if (!titre.isEmpty() || !contenu.isEmpty()) {
            List<Publication> list = ps.fetchPublicationByTitreAndContenu(titre, contenu);
            ObservableList<Publication> obs = FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
            contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("datepub"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        } else {
            initialize();
        }
    }
    @FXML
    void IdStatistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/statistiques.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

