package controller.front.Ecommerce;

import controller.Sponsoring.AfficherProduitFrontController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Ecommerce.Lignepanier;
import model.Ecommerce.Panier;
import model.Ecommerce.PanierItem;
import org.controlsfx.control.Notifications;
import service.Ecommerce.CommandeService;
import service.Ecommerce.LignepanierService;
import service.Ecommerce.PanierService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPanier implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonCommand;
    Label prixprodLabel;

    @FXML
    private Label DeletID;

    @FXML
    private Label NomProduitID;

    @FXML
    private Label PrixID;

    @FXML
    private Label QuantiteID;

    @FXML
    private Label TotalApres;

    @FXML
    private Label TotalAvant;

    @FXML
    private Label totalLabel;
     int Totalpanier;
    @FXML
    private Label TotalProduit;
    @FXML
    private Button BackEvent;

    @FXML
    private VBox vBoxContainer;

    @FXML
    private HBox idcard;

    @FXML
    private Label nbrproduit;

    @FXML
    private Button produitpage;

    @FXML
    private Label totalpn;
    private double total = 0;
    private List<PanierItem> objectList;
    private PanierService panierService = new PanierService();
    private LignepanierService lignepanierService = new LignepanierService();
    private CommandeService commandeService = new CommandeService();
    private int idPanier = commandeService.getPanierActif();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        objectList = panierService.afficherinfopanier(idPanier);

        String ch = "You currently have " + objectList.size() + " item(s) in your cart.";
        nbrproduit.setText(ch);
        Insets margins = new Insets(0, 50, 0, -10);
        for (PanierItem obj : objectList) {
            HBox hbox = createHBoxForItem(obj, margins);
            vBoxContainer.getChildren().addAll(hbox, createSeparator());
            int lastIndex = vBoxContainer.getChildren().size() - 1;
        }

        HBox totalBox = createTotalBox();
        vBoxContainer.getChildren().add(totalBox);
    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOpacity(0.4);
        return separator;
    }
    private void updateHBox(PanierItem obj,HBox hbox) {
        ObservableList<Node> children = hbox.getChildren();
        Label prixprodLabel = (Label) children.get(3);
        prixprodLabel.setText(String.valueOf(obj.getTotalProduit()) + " DT");
        hbox.getChildren().set(3, prixprodLabel);
    }

    private HBox createHBoxForItem(PanierItem obj, Insets margins) {
        HBox hbox = new HBox();
        hbox.setPadding(margins);
        Label nameLabel = new Label("  " + obj.getNomProduit() + "         ");
        Label priceLabel = new Label("   " + obj.getPrixUnitaire() + " DT   ");
        prixprodLabel = new Label("" + obj.getTotalProduit() + " DT  ");
        Button deleteButton = new Button("delet");


        Spinner<Integer> quant = new Spinner<>(1, Integer.MAX_VALUE, obj.getQuantite()); // Plage de valeurs de 0 à Integer.MAX_VALUE (valeur maximale d'un entier), valeur initiale à 0

        TotalApres.setText(String.valueOf(obj.getTotalpanier()+10) + " DT");
        TotalAvant.setText(String.valueOf(obj.getTotalpanier()) + " DT");
        quant.setPrefWidth(60);
        total = 0;
        for (PanierItem cc : objectList) {
            total += cc.getTotalProduit();
        }

        nameLabel.setStyle("-fx-text-fill: #ec1fbc;");
        hbox.getChildren().addAll(nameLabel, quant, priceLabel, prixprodLabel, deleteButton);
        hbox.setSpacing(60);
        hbox.setAlignment(Pos.TOP_CENTER);

        quant.valueProperty().addListener((obs, oldValue, newValue) -> {
            int newQuantity = newValue;
            float newPrice = newQuantity * obj.getPrixUnitaire();
            obj.setQuantite(newQuantity); // Mettre à jour la quantité dans l'objet PanierItem
            obj.setTotalProduit(newPrice); // Mettre à jour le prix total du produit dans l'objet PanierItem


            updateTotals(obj);
            updateHBox(obj,hbox);

        });

        deleteButton.setOnAction(event -> {
            try {
                lignepanierService.delete(obj.getIdpanier(), obj.getIdproduit());
                Panier panier = panierService.selectPanierById(obj.getIdpanier());
                int s = (int) (obj.getTotalpanier() - obj.getTotalProduit());
                panierService.update(panier);

                ((HBox) deleteButton.getParent()).getChildren().clear();

                objectList.clear();
                objectList = panierService.afficherinfopanier(idPanier);

                total = total - obj.getTotalProduit();
                totalLabel.setText("" + total + " DT");
                updateTotals(obj);

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update the order status!");
                alert.showAndWait();
            }
        });

        return hbox;
    }

    private HBox createTotalBox() {
        HBox totalBox = new HBox();
        totalBox.setSpacing(110);

        // Créez un label pour afficher le total
        Label NPLabel = new Label("Total");
        Label QttLabel = new Label("");
        Label pxLabel = new Label("");
        totalLabel = new Label("" + total + " DT");

        totalBox.getChildren().addAll(NPLabel, QttLabel, pxLabel, totalLabel);
        totalBox.setPadding(new Insets(0, 0, 10, 0));
        NPLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

        return totalBox;
    }

    @FXML
    void CommandForm(ActionEvent event) {
        try {
            if (Totalpanier == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Panier Vide");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez ajouter des produits à votre panier.");
                alert.showAndWait();
                return;
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/PasserCommande.fxml"));
            Parent root = loader.load();
            PasserCommandeContoller controller = loader.getController();
            controller.setIdp(idPanier);


            ButtonCommand.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }
    }


    private void updateTotals(PanierItem obj1) {
        // Calcul du total du panier
        double total = 0;
        for (PanierItem obj : objectList) {
            total += obj.getTotalProduit();

        }
        Totalpanier = (int) total;

        totalLabel.setText(String.valueOf(total) + " DT");
        TotalApres.setText(String.valueOf(total+10) + " DT");
        TotalAvant.setText(String.valueOf(total) + " DT");
        Panier panier = panierService.selectPanierById(obj1.getIdpanier());
        panier.setPrixTotal((int) total);
        try {
            panierService.update(panier);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Lignepanier ligne=lignepanierService.selectlignepanier(obj1.getIdpanier(), obj1.getIdproduit());
        ligne.setQuantité(obj1.getQuantite());
        try {
            lignepanierService.update(ligne);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showproducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Sponsoring/AfficherProduitF.fxml"));
            Parent root = loader.load();
            AfficherProduitFrontController controller = loader.getController();

            produitpage.getScene().setRoot(root);
        } catch (IOException e) {
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
