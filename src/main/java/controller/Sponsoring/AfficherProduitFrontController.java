package controller.Sponsoring;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.Blog.Commentaire;
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
import utils.Session;

public class AfficherProduitFrontController {
    @FXML
    private Button afficherpanier;

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
    private Button BackEvent;

    LignepanierService lignepanierService = new LignepanierService();
    PanierService panierService = new PanierService();
    CommandeService commandeService = new CommandeService();


    @FXML
    public void initialize() {
        //System.out.println("useris" + Session.getSession().getUser().getId());

       // List<Commentaire> list = cs.fetchCommentaireByUserID(Session.getSession().getUser().getId());
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

        File file = new File("C:/xampp8/htdocs/femHealthfinal/public/assets/uploads/product/" + produit.getImage());
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
        Ajouter.setLayoutX(15);
        Ajouter.setLayoutY(280);
        Ajouter.getStyleClass().add("evenement-reserver-button");
        card.getChildren().addAll(imageView, nomLabel, marqueLabel, prixLabel, newPriceLabel,Ajouter);

        Ajouter.setOnAction(event -> {
            try {
                int panierActifId = commandeService.getPanierActif();
                if (panierActifId == -1) {
                    System.out.println("panier cruer"+panierActifId);
                    Panier panier = new Panier();
                    // System.out.println("fi prod"+authentificationController.getUtilisateurConnecte().getId());
                    panier.setIdUser(utils.Session.getSession().getUser().getId());

                    panier.setPrixTotal(0);
                    panier.setStatut("En Cour");
                    panierService.add(panier);
                    System.out.println("hethi fi sone3 ta3 panier"+panier.getId());
                    Lignepanier lignepanier = new Lignepanier();
                    lignepanier.setQuantité(1);
                    lignepanier.setIdProduit(produit.getId());
                    lignepanier.setIdPanier(panier.getId());
                    lignepanierService.add(lignepanier);
                    panier.setPrixTotal(panierService.calculTotalPanier(panier.getId()));
                    panierService.update(panier);
                    System.out.println("idpanier loulljdid"+panier.getId());
                } else {
                    System.out.println("panier modifier"+panierActifId);
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

            afficherpanier.getScene().setRoot(root);
        } catch(IOException e) {
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }
    }
    @FXML
    void BackEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageEventF.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BackEvent.getScene().setRoot(root);
    }

}