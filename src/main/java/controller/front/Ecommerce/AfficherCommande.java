package controller.front.Ecommerce;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ecommerce.*;

import service.Ecommerce.PanierService;

public class AfficherCommande {

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

    @FXML
    void initialize(int idpanierafficher) {
        System.out.println("ID du panier apres la methode setIdpanierafficher : " +idpanierafficher);

        List<PanierItem> infosPanier = panierService.afficherinfopanier(idpanierafficher);
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
    }

}




