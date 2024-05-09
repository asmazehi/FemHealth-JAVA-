package controller.front.Ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ecommerce.PanierItem;
import service.Ecommerce.PanierService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailCommand {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button commandsClient;




    @FXML
    private Label nbrproduit;
    @FXML
    private Button ButtonCommand;



    @FXML
    private HBox idcard;





    @FXML
    private VBox vBoxContainer;
    private List<PanierItem> listItems;

    //int idPanier=2;



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
        List<PanierItem> objectList = panierService.afficherinfopanier(idp);
        String ch = "You currently have " + objectList.size() + " item(s) in your cart.";
        nbrproduit.setText(ch);
        Insets margins = new Insets(0, 0, 5, 0);
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
        hbox.setSpacing(100);
        hbox.setAlignment(Pos.TOP_CENTER);
        //idcard.setPadding(new Insets(0, 0, 0, 5000));
        return hbox;
    }
    private HBox createTotalBox(List<PanierItem> objectList) {
        HBox totalBox = new HBox();
        totalBox.setSpacing(150); // Espace entre les éléments

        // Calcul du total du panier
        double total = 0;
        for (PanierItem obj : objectList) {
            total += obj.getTotalProduit();
        }

        String totalpanierApresDtString = String.valueOf(total) + "DT";

        // Créez un label pour afficher le total
        Label NPLabel = new Label("Total");
        Label QttLabel = new Label("");
        Label pxLabel = new Label("");
        Label totalLabel = new Label("" + total + " DT");
        // Ajoutez le label au HBox
        totalBox.getChildren().addAll(NPLabel, QttLabel, pxLabel, totalLabel);
        totalBox.setPadding(new Insets(0, 0, 15, 0));
        NPLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 17px;");

        return totalBox;
    }



}
