package controller.back.Blog;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Blog.Commentaire;
import model.Blog.Publication;
import model.User.Utilisateur;
import service.Blog.CommentaireService;
import javafx.scene.control.cell.ComboBoxTableCell;
public class AfficherCommentaire {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TextField recherche;

    @FXML
    private TableView<Commentaire> table1view;
    @FXML
    private TableColumn<Commentaire, Boolean>  Action;
    @FXML
    private TableColumn<Commentaire, Date> dateCCol;
    @FXML
    private TableColumn<Commentaire, Boolean> ActionCol;
    @FXML
    private Label Welcome;
    @FXML
    private TableColumn<Commentaire, String> pubCol;
    @FXML
    private TableColumn<Commentaire, String> userCol;
    @FXML
    private TableColumn<Commentaire, String> descriptionCol;
    CommentaireService cs = new CommentaireService();
    ObservableList<Commentaire> obsC;
    @FXML
    void cd107b(ActionEvent event) {
    }
    @FXML
    void initialize() {
        try {
            recherche.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    searchCommentsByPublicationTitle(newValue);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
            List<Commentaire> list = cs.select();
            obsC= FXCollections.observableArrayList(list);
            table1view.setItems(obsC);
            dateCCol.setCellValueFactory(new PropertyValueFactory<>("datecomnt"));
            //ActionCol.setCellValueFactory(new PropertyValueFactory<>("active"));
            obsC.sort(Comparator.comparing(Commentaire::getDatecomnt).reversed());

            ActionCol.setCellFactory(col -> {
                TableCell<Commentaire, Boolean> cell = new TableCell<Commentaire, Boolean>() {
                    private final ChoiceBox<Boolean> choiceBox = new ChoiceBox<>();

                    {
                        choiceBox.getItems().addAll(true, false);


                        choiceBox.setOnAction(event -> {

                            Boolean selectedAction = choiceBox.getValue();
                            if (selectedAction != null) {

                                Commentaire commentaire = getTableRow().getItem();

                                if (commentaire != null && !(commentaire.isActive())==selectedAction) {
                                    if (confirmAction("Are you sure you want to change the state of this Commentaire?")) {
                                        commentaire.setActive(selectedAction);
                                        System.out.println("sssss  "+selectedAction);
                                        try {

                                            cs.ActiveDesactiveComentaire(commentaire);
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (!selectedAction) {

                                        }
                                        System.out.println("Updated Commentaire: " + commentaire);
                                    } else {

                                        choiceBox.setValue(commentaire.isActive());
                                    }
                                }
                            }
                        });
                    }


                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Commentaire commentaire = getTableRow().getItem();
                            if (commentaire != null) {
                                choiceBox.setValue(commentaire.isActive());
                                setGraphic(choiceBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
                return cell;
            });
            userCol.setCellValueFactory(cellData -> {
                Commentaire commentaire = cellData.getValue();
                Utilisateur utilisateur = commentaire.getUser_id();
                if (utilisateur != null) {
                    return new SimpleStringProperty(utilisateur.getNom());
                } else {
                    return new SimpleStringProperty("");
                }
            });
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            pubCol.setCellValueFactory(cellData -> {
                Commentaire commentaire = cellData.getValue();
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
     public void setData(String msg){
        Welcome.setText("Welcome" + msg);
    }
        private boolean confirmAction(String message) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
    private void searchCommentsByPublicationTitle(String publicationTitle) throws SQLException {
        if (!publicationTitle.isEmpty()) {
            List<Commentaire> list = cs.fetchCommentaireByCommentDescription(publicationTitle);
            obsC = FXCollections.observableArrayList(list);
            table1view.setItems(obsC);
        } else {
            initialize();
        }
    }
}
