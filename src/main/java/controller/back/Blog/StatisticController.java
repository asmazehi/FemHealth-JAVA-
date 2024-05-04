package controller.back.Blog;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import model.Blog.Commentaire;
import model.Blog.Publication;
import service.Blog.CommentaireService;
import service.Blog.PublicationService;

public class StatisticController {
    //private BarChart<String, Integer> barChart;

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
                // Supposons que vous ayez une méthode pour récupérer le titre de la publication à partir de son identifiant
                String publicationTitle = commentaireService.fetchPublicationTitleById(publicationId);
                series.getData().add(new XYChart.Data<>(publicationTitle, commentCount));
            }

            barChart.getData().add(series);
        } catch (SQLException e) {
            // Gérer les exceptions SQL ici
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
        // Count the comments
        return commentaires.size();

    }
    private List<Publication> fetchPublicationsFromDatabase() {
        List<Publication> publications = new ArrayList<>();

        // Simulation de la récupération des publications depuis une base de données
        for (int i = 1; i <= 10; i++) {
            String titre = "Publication " + i;
            String contenu = "Contenu de la publication " + i;
            String image = "image" + i + ".png";
            Date date = new Date();// Date fictive pour l'exemple

            // Créez une nouvelle publication avec les valeurs simulées
            Publication publication = new Publication(contenu, image, titre);
            // Ajoutez la publication à la liste
            publications.add(publication);
        }

        return publications;
    }
}
