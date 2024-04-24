package controller.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.events.Evenement;
import service.events.EvenementC;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AffichageEventF {

    @FXML
    private FlowPane eventFlowPane;

    private EvenementC evenementService = new EvenementC();

    @FXML
    public void initialize() {
        try {
            List<Evenement> evenementList = evenementService.select();

            for (Evenement evenement : evenementList) {
                AnchorPane card = createEvenementCard(evenement);
                eventFlowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }
    }

    private AnchorPane createEvenementCard(Evenement evenement) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 300); // Fixed size for each card

        // Add border to the card
        card.setStyle("-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 5px;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(15);
        imageView.setLayoutY(15);
        imageView.getStyleClass().add("evenement-image");

        File file = new File(evenement.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

        Label nomLabel = new Label(evenement.getNom());
        nomLabel.setLayoutX(15);
        nomLabel.setLayoutY(180);
        nomLabel.getStyleClass().add("evenement-nom");

        Label dateDebutLabel = new Label("Date début: " + evenement.getDateDebut());
        dateDebutLabel.setLayoutX(15);
        dateDebutLabel.setLayoutY(200);
        dateDebutLabel.getStyleClass().add("evenement-date-debut");

        Label dateFinLabel = new Label("Date fin: " + evenement.getDateFin());
        dateFinLabel.setLayoutX(15);
        dateFinLabel.setLayoutY(220);
        dateFinLabel.getStyleClass().add("evenement-date-fin");

        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        localisationLabel.setLayoutX(15);
        localisationLabel.setLayoutY(240);
        localisationLabel.getStyleClass().add("evenement-localisation");

        Label montantLabel = new Label("Montant: " + evenement.getMontant());
        montantLabel.setLayoutX(15);
        montantLabel.setLayoutY(260);
        montantLabel.getStyleClass().add("evenement-montant");

        card.getChildren().addAll(imageView, nomLabel, dateDebutLabel, dateFinLabel, localisationLabel, montantLabel);
        return card;
    }
}
