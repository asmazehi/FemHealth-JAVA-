package controller.front.Blog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import model.Blog.Publication;
import service.Blog.PublicationService;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane cardHolder;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    private static final int BLOG_PER_PAGE = 6;
    private int currentPage = 0;

    private ObservableList<CustomerCard> list = FXCollections.observableArrayList();
    private PublicationService allpublication = new PublicationService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Publication> publications = allpublication.select();

            for (int i = 0; i < publications.size(); i++) {
                System.out.println(publications.get(i).getImage());
                SimpleDateFormat sdfNouveau = new SimpleDateFormat("yyyy-MM-dd");
                String dateresult= sdfNouveau.format(publications.get(i).getDatepub());
                list.add(new CustomerCard(publications.get(i).getId(),
                        publications.get(i).getTitre(),
                        dateresult,
                        publications.get(i).getImage()));
            }

            int count = 0;
            int maxCardsPerRow = 3;
            double topMargin = 15;
            for (int i = 0; i < publications.size(); i += maxCardsPerRow) {
                for (int j = 0; j < maxCardsPerRow && (i + j) < publications.size(); j++) {
                    cardHolder.setMargin(list.get(count), new Insets(20,15 , 0, 0));

                    cardHolder.add(list.get(count), j, i / maxCardsPerRow);
                    count++;
                }

            }
            displayPublicationsPerPage(currentPage);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goToPrevPage(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            displayPublicationsPerPage(currentPage);
        }
    }

    @FXML
    void goToNextPage(ActionEvent event) {
        int totalPublications = list.size();
        int totalPages = (int) Math.ceil((double) totalPublications / BLOG_PER_PAGE);

        if (currentPage < totalPages - 1) {
            currentPage++;
            displayPublicationsPerPage(currentPage);
        }
    }

    private void displayPublicationsPerPage(int page) {
        int startIndex = page * BLOG_PER_PAGE;
        int endIndex = Math.min(startIndex + BLOG_PER_PAGE, list.size());

        cardHolder.getChildren().clear();
        for (int i = startIndex; i < endIndex; i++) {
            CustomerCard card = list.get(i);
            cardHolder.add(card, i % 3, i / 3);
        }
    }

    public void setPublication(Publication publication) {
        // Configure UI based on publication details
    }
}
