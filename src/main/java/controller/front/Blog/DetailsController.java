package controller.front.Blog;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.*;

public class DetailsController {
    public int idpub;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label date;
    @FXML
    private Label titre;
    @FXML
    private Label contenu;

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
                       /* ListComment.setAll(commentaireService.select(publicationSelectionné.getId()));
                        ListCommentView.setItems(ListComment);*/
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle the exception appropriately
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

    public void setListView() throws SQLException {
        CommentaireService cp=new CommentaireService();
        for (int i=0;i<cp.fetchCommentaireByPublicationID(this.idpub).size();i++){
            stringList.add((cp.fetchCommentaireByPublicationID(this.idpub).get(i).getDescription()));

            System.out.println(cp.fetchCommentaireByPublicationID(this.idpub).get(i).getDescription()+ " "+ cp.fetchCommentaireByPublicationID(this.idpub).get(i).getDatecomnt());

        }


        observableList.setAll(stringList);

        ListComment.setItems(observableList);

        ListComment.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> listView) {
                        return new ListeViewCell();
                    }
                });
    }

    @FXML
    void initialize() throws SQLException {

        initializeDetails();
        setListView();



    }

    public void setPublicationId(int publicationId) {
        this.idpub=publicationId;
    }
    @FXML
    void userCommentaire(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Blog/UserComments.fxml"));
            Parent root = loader.load();
           loader.getController();



            Scene scene = new Scene(root);


            Stage stage = new Stage();
            stage.setScene(scene);


            stage.initModality(Modality.APPLICATION_MODAL);


            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // G?rer l'exception de chargement du fichier FXML
        }
    }

}