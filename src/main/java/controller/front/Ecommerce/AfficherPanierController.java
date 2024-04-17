package controller.front.Ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ecommerce.PanierItem;
import service.Ecommerce.PanierService;
import controller.front.Ecommerce.DeleteButtonController;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class AfficherPanierController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

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
        private TableColumn<PanierItem, Float> price;

        @FXML
        private TableColumn<PanierItem, String> produitN;

        @FXML
        private TableColumn<PanierItem, Integer> quantiteColumn;

        @FXML
        private TableColumn<PanierItem, Float> totalpr;

        @FXML
        private TableView<PanierItem> table;
        @FXML
        private TableColumn<PanierItem, Void> deletCol;

        PanierService panierService= new PanierService();

        int idPanier = 4;

        @FXML
        void initialize() {
            List<PanierItem> infosPanier = panierService.afficherinfopanier(idPanier);
            List<PanierItem> panierItems = new ArrayList<>(); // Créer la liste en dehors de la boucle
            int size=infosPanier.size();
            String ch="You currently have "+size+" item(s) in your cart.";

            // Déplacer l'accès à un élément de la liste après avoir ajouté des éléments dans la boucle
            for (PanierItem info : infosPanier) {
                int idpanier=info.getIdpanier();
                int idproduit=info.getIdproduit();
                int quantite = info.getQuantite();
                float prix = info.getPrixUnitaire();
                String nomProduit = info.getNomProduit();
                float totalpanier = info.getTotalpanier();
                String statut = info.getStatus();
                PanierItem item = new PanierItem(idpanier,idproduit,nomProduit, prix, quantite, totalpanier, statut);
                panierItems.add(item); // Ajouter chaque élément à la liste à l'intérieur de la boucle
            }

            // Accès à un élément de la liste après avoir ajouté des éléments dans la boucle
            if (!panierItems.isEmpty()) {
                float totalpanierDt = panierItems.get(0).getTotalpanier(); // Accès à l'élément à l'index 0
                String totalpanierDtString = String.valueOf(totalpanierDt)+"DT";
                float totalpanierApresDT = totalpanierDt + 10;
                String totalpanierApresDtString = String.valueOf(totalpanierApresDT)+"DT";
                TotalAvant.setText(totalpanierDtString);
                totalpn.setText(totalpanierDtString);
                TotalApres.setText(totalpanierApresDtString);
                nbrproduit.setText(ch);
            }

            ObservableList<PanierItem> obs = FXCollections.observableArrayList(panierItems);
            table.setItems(obs);
            produitN.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
            price.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
            quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            totalpr.setCellValueFactory(new PropertyValueFactory<>("totalProduit"));
            deletCol.setCellFactory(column -> new DeleteButtonController(obs));
        }
/*@FXML
    public  void passerCommande(ActionEvent event){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource(/controller/front/Ecommerce/PasserCommandeContoller))
            }

}*/
    }






