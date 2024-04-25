package controller.Sponsoring;

import controller.front.Ecommerce.PasserCommandeContoller;
import controller.front.Ecommerce.ShowPanier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Ecommerce.Lignepanier;
import model.Ecommerce.Panier;
import model.Sponsoring.Produit;
import service.Ecommerce.CommandeService;
import service.Ecommerce.LignepanierService;
import service.Ecommerce.PanierService;
import service.Sponsoring.ProduitService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherProduitFrontController {

    @FXML
    private Button afficherpanier;

    @FXML
    private FlowPane produitFlowPane;

    LignepanierService lignepanierService = new LignepanierService();
    PanierService panierService = new PanierService();
    CommandeService commandeService = new CommandeService();
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
        Button Ajouter = new Button("Ajouter");


        Ajouter.setOnAction(event -> {
            try {
                int panierActifId = commandeService.getPanierActif();
                if (panierActifId == -1) {
                    Panier panier = new Panier();
                    panier.setIdUser(1);
                    panier.setPrixTotal(0);
                    panier.setStatut("En Cour");
                    panierService.add(panier);

                    Lignepanier lignepanier = new Lignepanier();
                    lignepanier.setQuantité(1);
                    lignepanier.setIdProduit(produit.getId());
                    lignepanier.setIdPanier(panier.getId());
                    lignepanierService.add(lignepanier);
                    panier.setPrixTotal(panierService.calculTotalPanier(panier.getId()));
                    panierService.update(panier);
                } else {
                    Lignepanier lignePanierExistante = lignepanierService.selectlignepanier(panierActifId, produit.getId());
                    if (lignePanierExistante == null) {
                        Lignepanier lignepanier2 = new Lignepanier();
                        lignepanier2.setQuantité(1);
                        lignepanier2.setIdProduit(produit.getId());
                        lignepanier2.setIdPanier(panierActifId);
                        lignepanierService.add(lignepanier2);
                    } else {
                        lignePanierExistante.setQuantité(lignePanierExistante.getQuantité() + 1);
                        lignepanierService.update(lignePanierExistante);
                    }
                    Panier panier = panierService.selectPanierById(panierActifId);
                    panier.setPrixTotal(panierService.calculTotalPanier(panier.getId()));
                    panierService.update(panier);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        card.getChildren().addAll(imageView, nomLabel, marqueLabel, prixLabel, newPriceLabel, Ajouter);
        return card;
    }
    @FXML
    void afficherpanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/ShowPanier1.fxml"));
            Parent root = loader.load();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Afficher Panier");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment afficher le panier?");
            alert.showAndWait();

            // Modification de la scène pour afficher le panier
            afficherpanier.getScene().setRoot(root);
        } catch(IOException e) {
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }
    }


}
