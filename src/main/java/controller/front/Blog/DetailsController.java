package controller.front.Blog;

import utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import service.Blog.PublicationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.text.StyleConstants.Bold;

public class DetailsController {
    public int idpub;
    @FXML
    private ResourceBundle resources;
    @FXML
    private ChoiceBox<String> BoxConx;
    @FXML
    private ScrollPane idcomentaire;

    @FXML
    private URL location;
    @FXML
    private Label date;
    @FXML
    private Label titre;
    @FXML
    private Label contenu;
    private Session session;
    @FXML
    private ListView<String> ListComment;
    @FXML
    private ImageView imageView;
    private List<String> stringList = new ArrayList<>();
    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private TextArea AreaComment;

    CommentaireService cs=new CommentaireService();
    ObservableList<Commentaire> obs;
    ObservableList<cardcomentairepub> list = FXCollections.observableArrayList();
    @FXML
    void AddCommentaire(ActionEvent event) throws SQLException {
        CommentaireService commentaireService = new CommentaireService();
        Publication publicationSelectionné = commentaireService.fetchPublicationById(idpub);
        if (publicationSelectionné != null) {
            String commentaireText = AreaComment.getText();
            if (!commentaireText.isEmpty()) {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Voulez-vous vraiment ajouter ce commentaire ?");
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si l'utilisateur clique sur "OK", ajouter le commentaire
                    Commentaire commentaire = new Commentaire();
                    commentaire.setDescription(commentaireText);

                    commentaire.setPublication(publicationSelectionné); // Set the associated publication

                    try {
                        commentaireService.add(commentaire);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Commentaire ajouté");
                        alert.setHeaderText(null);
                        alert.setContentText("Commentaire ajouté avec succès!");
                        alert.setContentText("Commentaire ajouté avec succès!");
                        alert.showAndWait();
                        AreaComment.clear();
                        initialize();
                       /* ListComment.setAll(commentaireService.select(publicationSelectionné.getId()));
                        ListCommentView.setItems(ListComment);*/
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Display an error message if the comment text is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("entrez un commentaire!");
                alert.showAndWait();
            }
        }
    }
    public void setIdpub(int id) {
        this.idpub=id;
        System.out.println(id);
    }
    public  int getIdpub(){
        return  this.idpub;
    }
    CommentaireService commentaireService=new CommentaireService();
    public void initializeDetails() throws SQLException {
        PublicationService allpublication = new PublicationService();
        Publication pub=  commentaireService.fetchPublicationById(this.idpub);
        titre.setText(pub.getTitre());
        contenu.setText(pub.getContenu());
        System.out.println(pub.getImage());
        System.out.println(pub.getImage());
        date.setText(pub.getDatepub()+"");
        String imageUrl = pub.getImage();
        if (imageUrl == null || imageUrl.isEmpty()) {
            System.out.println("url not found");
        }else{
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        }
        setListView();
    }
    public void choiceBoxConnexion(){

    }
    /*
    *  CommentaireService cp = new CommentaireService();
        List<Commentaire> commentaires = cp.fetchCommentaireByPublicationID(this.idpub);

        List<Commentaire> commentairesActifs = commentaires.stream()
                .filter(Commentaire::isActive)
                .collect(Collectors.toList());

        for (Commentaire commentaire : commentairesActifs) {
            String descirption="description";

            stringList.add( "description: "+commentaire.getDescription()+"\n"+" date:"+ commentaire.getDatecomnt());
            System.out.println(commentaire.getDescription() + " " + commentaire.getDatecomnt());
        }

        observableList.setAll(stringList);
        ListComment.setItems(observableList);
        ListComment.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListeViewCell();
            }
        });*/
    public void setListView() throws SQLException {
        try {
            CommentaireService cp = new CommentaireService();
            List<Commentaire> commentaires = cp.fetchCommentaireByPublicationID(this.idpub);

            VBox commentaireContainer = new VBox();
            commentaireContainer.setSpacing(10);

            for (Commentaire commentaire : commentaires) {
                SimpleDateFormat sdfNouveau = new SimpleDateFormat("yyyy-MM-dd");
                String dateresult = sdfNouveau.format(commentaire.getDatecomnt());

                HBox commentaireContent = new HBox();

                ImageView likeIcon = new ImageView("/Front/Blog/like.png");
                likeIcon.setFitHeight(20);
                likeIcon.setFitWidth(20);

                likeIcon.setOnMouseClicked(event -> {
                    commentaire.setBrlike(commentaire.getBrlike() + 1);
                    commentaire.setId(commentaire.getId());
                    System.out.println("like " + commentaire.getBrlike());
                    commentaireService.incrimentelike(commentaire);
                    try {

                        updateListView();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                if (commentaire.getBrlike() > 0) {
                    String nbrlike = commentaire.getBrlike() + "";
                    commentaireContent.getChildren().addAll(new Label(commentaire.getDescription() + " "), likeIcon, new Label(" " + commentaire.getBrlike()));
                } else {
                    commentaireContent.getChildren().addAll(new Label(commentaire.getDescription() + " "), likeIcon);
                }

                Label username = new Label(commentaire.getUser_id().getNom());
                username.setStyle("-fx-font-family: 'SansSerif'; -fx-font-weight: bold; -fx-font-size: 14px;");
                Label dateLabel = new Label(dateresult);

                VBox commentaireBox = new VBox(username, commentaireContent, dateLabel);
                commentaireBox.setStyle("-fx-border-color: black; -fx-padding: 10px;");

                commentaireContainer.getChildren().add(commentaireBox);
            }

            idcomentaire.setContent(commentaireContainer);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private void updateListView() throws SQLException {
        setListView();


        Label updatedLabel = new Label("is updated");
        updatedLabel.setStyle("-fx-font-weight: bold;");
        ((VBox) idcomentaire.getContent()).getChildren().add(updatedLabel);
    }
    @FXML
    void initialize() throws SQLException {

        initializeDetails();
        setListView();
        // Définir une action à effectuer lorsqu'une option est sélectionnée




    }
    private void afficherMessageDeconnexion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez été déconnecté avec succès.");
        alert.showAndWait();
    }
    public void setPublicationId(int publicationId) {
        this.idpub=publicationId;
    }
    @FXML
    void userCommentaire(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Blog/UserComments.fxml"));
            Parent root = loader.load();
            loader.getController();



            Scene scene = new Scene(root);


            Stage stage = new Stage();
            stage.setScene(scene);


            stage.initModality(Modality.APPLICATION_MODAL);


            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void BackTo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Blog/carCard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) AreaComment.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}