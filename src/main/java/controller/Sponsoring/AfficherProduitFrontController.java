package controller.Sponsoring;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.Sponsoring.Produit;
import service.Sponsoring.ProduitService;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AfficherProduitFrontController {

    @FXML
    private FlowPane produitFlowPane;

    @FXML
    private ChoiceBox<String> categorieComboBox;

    @FXML
    private Pagination pagination;

    private ProduitService ps = new ProduitService();
    private List<Produit> produitList;

    private static int ITEMS_PER_PAGE = 3;

    @FXML
    public void initialize() {
        try {
            produitList = ps.select();

            if (produitList.size() < ITEMS_PER_PAGE) {
                ITEMS_PER_PAGE = produitList.size();
            }

            pagination.setPageCount((int) Math.ceil((double) produitList.size() / ITEMS_PER_PAGE));
            pagination.setPageFactory(this::createPage);

            // Show navigation buttons
            pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

            // Add scroll event listener
            pagination.setOnScroll(event -> {
                if (event.getDeltaY() < 0) {
                    pagination.setCurrentPageIndex(Math.min(pagination.getCurrentPageIndex() + 1, pagination.getPageCount() - 1));
                } else {
                    pagination.setCurrentPageIndex(Math.max(pagination.getCurrentPageIndex() - 1, 0));
                }
                event.consume();
            });
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }




    private AnchorPane createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, produitList.size());
        List<Produit> subList = produitList.subList(fromIndex, toIndex);

        AnchorPane pageAnchorPane = new AnchorPane();
        pageAnchorPane.setPrefSize(566, 520);

        FlowPane pageFlowPane = new FlowPane();
        pageFlowPane.setPrefWidth(566);
        pageFlowPane.setPrefHeight(520);

        for (Produit produit : subList) {
            AnchorPane card = createProduitCard(produit);
            pageFlowPane.getChildren().add(card);
        }

        pageAnchorPane.getChildren().add(pageFlowPane);

        return pageAnchorPane;
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


    @FXML
    private void filterByCategory() {
        try {
            String selectedCategory = categorieComboBox.getValue();
            if (selectedCategory.equals("Toutes les catégories")) {
                produitList = ps.select();
            } else {
                produitList = ps.selectByCategory(selectedCategory);
            }

            pagination.setPageCount((int) Math.ceil((double) produitList.size() / ITEMS_PER_PAGE));
            pagination.setCurrentPageIndex(0); // Go to the first page
            pagination.setPageFactory(this::createPage); // Update the displayed products
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }
}
