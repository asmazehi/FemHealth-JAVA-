package controller.back.Blog;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import service.Blog.PublicationService;

public class StatisticController {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private BarChart<String, Number> barChart;
    private ObservableList<String> publicationTitles = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        try {
            PublicationService publicationService = new PublicationService();
            CommentaireService commentaireService= new CommentaireService();
            List<Object[]> publicationData = publicationService.findPublicationWithCommentCount();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Object[] data : publicationData) {
                int publicationId = (int) data[0];
                int commentCount = (int) data[1];
                 String publicationTitle = commentaireService.fetchPublicationTitleById(publicationId);
                series.getData().add(new XYChart.Data<>(publicationTitle, commentCount));
            }

            barChart.getData().add(series);
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    public void setPublicationData(List<Publication> publications) throws SQLException {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Publication publication : publications) {
            int numComments = countCommentsForPublication(publication);
            series.getData().add(new XYChart.Data<>(publication.getTitre(), numComments));
            System.out.println(countCommentsForPublication(publication));
        }
        barChart.getData().add(series);
    }
    private int countCommentsForPublication(Publication publication) throws SQLException {
        CommentaireService commentaireService = new CommentaireService();
        List<Commentaire> commentaires = commentaireService.fetchCommentaireByPublicationID(publication.getId());
         return commentaires.size();

    }
    private List<Publication> fetchPublicationsFromDatabase() {
        List<Publication> publications = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            String titre = "Publication " + i;
            String contenu = "Contenu de la publication " + i;
            String image = "image" + i + ".png";
            Date date = new Date();


            Publication publication = new Publication(contenu, image, titre);
            publications.add(publication);
        }

        return publications;
    }
    @FXML
    void BackAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/AfficherPublication.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) barChart.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
