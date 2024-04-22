package controller.front.Ecommerce;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ecommerce.PanierItem;
import service.Ecommerce.PanierService;
import controller.front.Ecommerce.DeleteButtonController;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class AfficherPanierController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;


        @FXML
        private Button ButtonCommand;

        @FXML
        private Label TotalApres;

        @FXML
        private Label TotalAvant;

        @FXML
        private Label totalpn;
        @FXML
        private Label nbrproduit;
        @FXML
        private TableColumn<PanierItem, Float> price;

        @FXML
        private TableColumn<PanierItem, String> produitN;

        @FXML
        private TableColumn<PanierItem, Void> quantiteColumn;

        @FXML
        private TableColumn<PanierItem, Float> totalpr;

        @FXML
        private TableView<PanierItem> table;
        @FXML
        private TableColumn<PanierItem, Void> deletCol;
        @FXML
        private Button produitpage;


        PanierService panierService= new PanierService();

        public int idPanier =8;

    @FXML
    void verspageproduct(ActionEvent event) {
        try {
            System.out.println("Méthode verspageproduct() appelée.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/PasserCommande.fxml"));
            Parent root = loader.load();
            PasserCommandeContoller controller = loader.getController();
            produitpage.getScene().setRoot(root);
        }catch(IOException e){
            System.err.println("Error loading PasserCommande.fxml: " + e.getMessage());
        }

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



    private ObservableList<PanierItem> panierItems = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        System.out.println("Méthode initialize() appelée.");
        List<PanierItem> infosPanier = panierService.afficherinfopanier(idPanier);
        float totalpanierDt = 0;
        String ch = "You currently have " + infosPanier.size() + " item(s) in your cart.";

        for (PanierItem info : infosPanier) {
            int idpanier = info.getIdpanier();
            int idproduit = info.getIdproduit();
            int quantite = info.getQuantite();
            float prix = info.getPrixUnitaire();
            String nomProduit = info.getNomProduit();
            float totalpanier = info.getTotalpanier();
            String statut = info.getStatus();
            PanierItem item = new PanierItem(idpanier, idproduit, nomProduit, prix, quantite, totalpanier, statut);
            panierItems.add(item); // Ajouter chaque élément à l'ObservableList
            totalpanierDt += info.getTotalProduit();
        }

        // Mettre à jour l'affichage avec les nouvelles valeurs
        updateDisplay(totalpanierDt, ch);

        // Ajouter le listener pour détecter les changements dans la liste
        observeListChanges();
    }

    private void observeListChanges() {
        panierItems.addListener((ListChangeListener.Change<? extends PanierItem> change) -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    // Un élément a été supprimé de la liste
                    float newTotal = calculateTotal();
                    // Mettre à jour l'affichage avec les nouvelles valeurs
                    updateDisplay(newTotal, "You currently have " + panierItems.size() + " item(s) in your cart.");
                }
            }
        });
    }

    private float calculateTotal() {
        float total = 0;
        for (PanierItem item : panierItems) {
            total += item.getTotalProduit();
        }
        return total;
    }

    private void updateDisplay(float totalpanierDt, String ch) {
        String totalpanierDtString = String.valueOf(totalpanierDt) + "DT";
        float totalpanierApresDT=totalpanierDt;
        if(totalpanierDt !=0){
            totalpanierApresDT = totalpanierDt + 10;}


        String totalpanierApresDtString = String.valueOf(totalpanierApresDT) + "DT";
        TotalAvant.setText(totalpanierDtString);
        totalpn.setText(totalpanierDtString);
        TotalApres.setText(totalpanierApresDtString);
        nbrproduit.setText(ch);
        produitN.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
        price.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        //quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        quantiteColumn.setCellFactory(column-> new BouttonModifierQuantiteController(panierItems,table));

        totalpr.setCellValueFactory(new PropertyValueFactory<>("totalProduit"));
        table.setItems(panierItems);
        deletCol.setCellFactory(column -> new DeleteButtonController(panierItems));
    }

}






