package controller.front.Ecommerce;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ecommerce.*;

import service.Ecommerce.CommandeService;
import service.Ecommerce.PanierService;
import org.controlsfx.control.Notifications;

public class AfficherCommande {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button BouttonAnnuler;

    @FXML
    private Button BouttonConfirmer;
    @FXML
    private Label idStatut;

    @FXML
    private Button commandsClient;

    @FXML
    private Label TotalApres;

    @FXML
    private Label TotalAvant;

    @FXML
    private Label totalpn;
    @FXML
    private Label nbrproduit;
    @FXML
    private Button ButtonCommand;



    @FXML
    private HBox idcard;



    @FXML
    private Button produitpage;

    @FXML
    private VBox vBoxContainer;
    private List<PanierItem> listItems;
    private CommandeService commandeService= new CommandeService();

    int idpanier;



    PanierService panierService= new PanierService();

    @FXML
    void showcommnands(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherListCommandeparClient.fxml"));
            Parent root = loader.load();
            //AfficherPanierController controller = loader.getController();
            commandsClient.getScene().setRoot(root);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }




    public void initialize(int idp) {
        idStatut.setVisible(false);
        idpanier=idp;
        List<PanierItem> objectList = panierService.afficherinfopanier(idp);
        idcard.setSpacing(1000);
        String ch = "You currently have " + objectList.size() + " item(s) in your cart.";
        nbrproduit.setText(ch);
        Insets margins = new Insets(0, 50, 0, -10);
        for (PanierItem obj : objectList) {
            HBox hbox = createHBoxForItem(obj,margins);
            vBoxContainer.getChildren().addAll(hbox,createSeparator());
            int lastIndex = vBoxContainer.getChildren().size() - 1;
        }
        HBox totalBox = createTotalBox(objectList);
        vBoxContainer.getChildren().add(totalBox);

    }

    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOpacity(0.4);
        return separator;
    }


    private HBox createHBoxForItem(PanierItem obj,Insets margins) {
        HBox hbox = new HBox();
        hbox.setPadding(margins);
        Label nameLabel = new Label("  "+obj.getNomProduit()+"                 ");
        Label quantityLabel = new Label("    "+obj.getQuantite()+"");
        Label priceLabel = new Label("   " + obj.getPrixUnitaire()+" DT    ");
        Label prixprodLabel = new Label("" + obj.getTotalProduit()+" DT  ");




        nameLabel.setStyle("-fx-text-fill: #ec1fbc;"); // Couleur du texte en rose
        hbox.getChildren().addAll(nameLabel, quantityLabel, priceLabel, prixprodLabel);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.TOP_CENTER);
        //idcard.setPadding(new Insets(0, 0, 0, 5000));
        return hbox;
    }
    private HBox createTotalBox(List<PanierItem> objectList) {
        HBox totalBox = new HBox();
        totalBox.setSpacing(80); // Espace entre les éléments

        // Calcul du total du panier
        double total = 0;
        for (PanierItem obj : objectList) {
            total += obj.getTotalProduit();
        }
        double totalpanierApresDT2 = 0;
        if (total != 0) {
            totalpanierApresDT2 = total + 10;
        }
        String totalpanierApresDtString = String.valueOf(total) + "DT";
        TotalAvant.setText(totalpanierApresDtString);
        String totalpanierApresDtString2 = String.valueOf(totalpanierApresDT2) + "DT";
        TotalApres.setText(totalpanierApresDtString2);

        // Créez un label pour afficher le total
        Label NPLabel = new Label("Total");
        Label QttLabel = new Label("");
        Label pxLabel = new Label("");
        Label totalLabel = new Label("" + total + " DT");
        // Ajoutez le label au HBox
        totalBox.getChildren().addAll(NPLabel, QttLabel, pxLabel, totalLabel);
        totalBox.setPadding(new Insets(0, 0, 10, 0));
        NPLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

        return totalBox;
    }

    @FXML
    void AnnulerAction(ActionEvent event) {
        try {
            Commande commande = commandeService.selectCommandeByPanierId(idpanier);
            commande.setStatut("Annulée");
            commandeService.update(commande);

            // Afficher la notification d'annulation de commande
            Notifications.create()
                    .title("Annulation de commande")
                    .text("Votre commande a été annulée avec succès.")
                    .showWarning();

            System.out.println("id commande fi afficher" + commande.getId());
            System.out.println("id commande fi afficher" + commande.getStatut());
            BouttonAnnuler.setVisible(false);
            BouttonConfirmer.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }



    @FXML
    void ConfirmerAction(ActionEvent event) {
        BouttonConfirmer.setVisible(false);
        BouttonAnnuler.setVisible(false);

        Notifications.create()
                .title("Confirmation de commande")
                .text("Votre commande a été confirmée avec succès.")
                .showInformation();
    }

}




