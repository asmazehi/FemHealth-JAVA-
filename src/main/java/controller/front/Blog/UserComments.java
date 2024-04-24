package controller.front.Blog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.back.Blog.ModifierPublicationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;

public class UserComments {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Commentaire, String> descriptionCl;
    @FXML
    private TableColumn<Commentaire, Date> dateCl;
    @FXML
    private TableView<Commentaire> tableView;
    @FXML
    private TableColumn<Commentaire, String> publicationCl;
    CommentaireService cs = new CommentaireService();
    ObservableList<Commentaire> obs ;
    @FXML
    void modifierCommentaire(ActionEvent event) {
        Commentaire commentaireSelected = tableView.getSelectionModel().getSelectedItem();
        if (commentaireSelected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune commentaire sélectionnée");
            alert.setContentText("Veuillez sélectionner un commentaire à modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier le commentaire");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cet commentaire ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Blog/updateComment.fxml"));
            try {
                Parent root = loader.load();
                dialog.getDialogPane().setContent(root);
                UpdateComment updateComment= loader.getController();
                updateComment.setData(commentaireSelected.getId());
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.setUserData(this);
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
    void deleteComment(ActionEvent event) throws SQLException {
        try {
            Commentaire c=tableView.getSelectionModel().getSelectedItem();
            cs.delete(c.getId());
            System.out.println(c.getId());
            obs.remove(c);
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void initialize()  {
        try {
            List<Commentaire> list = cs.fetchCommentaireByUserID(3);
            obs= FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            descriptionCl.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateCl.setCellValueFactory(new PropertyValueFactory<>("datecomnt"));

            publicationCl.setCellValueFactory(cellData -> {
                Commentaire commentaire = cellData.getValue();
                System.out.println(commentaire);
                Publication publication = commentaire.getPublication();
                if (publication != null) {
                    return new SimpleStringProperty(publication.getTitre());
                } else {
                    return new SimpleStringProperty("");
                }
            });
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
}
