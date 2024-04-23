package controller.front.Ecommerce;
import javafx.geometry.Insets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Ecommerce.Panier;
import model.Ecommerce.PanierItem;
import service.Ecommerce.LignepanierService;
import service.Ecommerce.PanierService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShowPanier implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonCommand;

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
    @FXML
    private Label TotalProduit;
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

    private List<PanierItem> listItems;
    PanierService panierService= new PanierService();
    LignepanierService lignepanierService=new LignepanierService();
    int idPanier=2;
    @FXML
    double total=0;
    List<PanierItem> objectList = panierService.afficherinfopanier(idPanier);

    @Override
        public void initialize(URL location, ResourceBundle resources) {
            String ch = "You currently have " + objectList.size() + " item(s) in your cart.";
            nbrproduit.setText(ch);
            Insets margins = new Insets(0, 50, 0, -10);
            for (PanierItem obj : objectList) {
                HBox hbox = createHBoxForItem(obj,margins);
                vBoxContainer.getChildren().addAll(hbox,createSeparator());
                int lastIndex = vBoxContainer.getChildren().size() - 1;
            }

            HBox totalBox = createTotalBox(total);
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
        Label nameLabel = new Label("  "+obj.getNomProduit()+"             ");
        Label priceLabel = new Label("   " + obj.getPrixUnitaire()+" DT  ");
        Label prixprodLabel = new Label("" + obj.getTotalProduit()+" DT  ");
        Button delet = new Button("Delet");
        Spinner<Integer> quant=new Spinner<>(1, Integer.MAX_VALUE, obj.getQuantite()); // Plage de valeurs de 0 à Integer.MAX_VALUE (valeur maximale d'un entier), valeur initiale à 0

        quant.setPrefWidth(60);
         total=0;
        for (PanierItem cc: objectList) {
            total += cc.getTotalProduit();
        }


        nameLabel.setStyle("-fx-text-fill: #ec1fbc;"); // Couleur du texte en rose
        hbox.getChildren().addAll(nameLabel, quant, priceLabel, prixprodLabel,delet);
        hbox.setSpacing(18);
        hbox.setAlignment(Pos.TOP_CENTER);

        delet.setOnAction(event -> {
            try {
                lignepanierService.delete(obj.getIdpanier(), obj.getIdproduit());
                Panier panier=panierService.selectPanierById(obj.getIdpanier());
                int s = (int) (obj.getTotalpanier() - obj.getTotalProduit());
                panier.setPrixTotal(s);
                panierService.update(panier);
                // Supprimer le HBox parent
                ((HBox) delet.getParent()).getChildren().clear();

                objectList.clear();
                objectList = panierService.afficherinfopanier(idPanier);

                total =total- obj.getTotalProduit(); // Soustrayez le total du prix du produit supprimé
                totalLabel.setText("" + total + " DT"); // Mettez à jour le texte de l'étiquette du total
                // Mettre à jour les totaux
                updateTotals(objectList);




            } catch (SQLException e) {
                // Afficher une alerte en cas d'échec de mise à jour
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update the order status!");
                alert.showAndWait();
            }
        });

        //idcard.setPadding(new Insets(0, 0, 0, 5000));
        return hbox;
    }
    private HBox createTotalBox(double total) {

        HBox totalBox = new HBox();
        totalBox.setSpacing(80); // Espace entre les éléments

        // Calcul du total du panier

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
         totalLabel = new Label("" + total + " DT"); // Initialisez la référence à l'étiquette du total
        // Ajoutez le label au HBox
        totalBox.getChildren().addAll(NPLabel, QttLabel, pxLabel, totalLabel);
        totalBox.setPadding(new Insets(0, 0, 10, 0));
        NPLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

        return totalBox;
    }
@FXML
    void CommandForm(ActionEvent event) {
        try {
            System.out.println("Méthode CommandForm appelée.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/PasserCommande.fxml"));
            Parent root = loader.load();
            PasserCommandeContoller controller = loader.getController();
            controller.setIdp(idPanier);
            ButtonCommand.getScene().setRoot(root);
        }catch(IOException e){
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }
    }

    private void updateTotals(List<PanierItem> objectList) {
        // Calcul du total du panier
        double total = 0;
        for (PanierItem obj : objectList) {
            total += obj.getTotalProduit();
        }
        double totalpanierApresDT2 = total != 0 ? total + 10 : 0; // Mise à jour du total après
        String totalpanierApresDtString = String.valueOf(total) + "DT";
        String totalpanierApresDtString2 = String.valueOf(totalpanierApresDT2) + "DT";

        // Mise à jour des labels du total
        TotalAvant.setText(totalpanierApresDtString);
        TotalApres.setText(totalpanierApresDtString2);

    }


}