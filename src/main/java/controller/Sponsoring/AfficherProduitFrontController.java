package controller.Sponsoring;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.Sponsoring.Produit;
import service.Sponsoring.ProduitService;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AfficherProduitFrontController {

    @FXML
    private FlowPane produitFlowPane;

    private ProduitService ps = new ProduitService();

    @FXML
    public void initialize() {
        try {
            List<Produit> produitList = ps.select();

            for (Produit produit : produitList) {
                AnchorPane card = createProduitCard(produit);
                produitFlowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }

    private AnchorPane createProduitCard(Produit produit) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 300); // Taille fixe pour chaque carte

        // Ajouter un contour à la carte
        card.setStyle("-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 5px;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(15);
        imageView.setLayoutY(15);
        imageView.getStyleClass().add("produit-image");

        File file = new File(produit.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

        Label nomLabel = new Label(produit.getNom());
        nomLabel.setLayoutX(15);
        nomLabel.setLayoutY(180);
        nomLabel.getStyleClass().add("produit-nom");

        Label marqueLabel = new Label("Marque: " + produit.getSponsor().getNom());
        marqueLabel.setLayoutX(15);
        marqueLabel.setLayoutY(200);
        marqueLabel.getStyleClass().add("produit-marque");

        double newPrice = produit.getPrix() - (produit.getPrix() * produit.getTaux_remise() / 100);

        Label prixLabel = new Label("Prix: " + String.format("%.2f", produit.getPrix()) + " DT (" + produit.getTaux_remise() + "% de remise)");
        prixLabel.setLayoutX(15);
        prixLabel.setLayoutY(220);
        prixLabel.getStyleClass().add("produit-prix");

        Label newPriceLabel = new Label("Nouveau Prix: " + String.format("%.2f", newPrice) + " DT");
        newPriceLabel.setLayoutX(15);
        newPriceLabel.setLayoutY(240);
        newPriceLabel.setTextFill(Color.BLACK);
        newPriceLabel.getStyleClass().add("produit-nouveau-prix");

        card.getChildren().addAll(imageView, nomLabel, marqueLabel, prixLabel, newPriceLabel);
        return card;
    }


}