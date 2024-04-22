package controller.front.Ecommerce;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Ecommerce.*;
import service.Ecommerce.CommandeService;
import service.Ecommerce.PanierService;

public class AfficherListCommandeparClient {
    @FXML
    private TableColumn<Commande, Void> Mstatut;
    @FXML
    private TableColumn<Commande,Void> DetailCol;

    @FXML
    private TableColumn<Commande,Date> DateC;

    @FXML
    private TableColumn<Commande, String> StatutCol;

    @FXML
    private Button addC;

    @FXML
    private TableColumn<Commande, String> adressCol;

    @FXML
    private TableColumn<Commande, String> livraisonCol;

    @FXML
    private TableColumn<Commande, String> paiementCol;

    @FXML
    private TableColumn<Commande, String> phoneCol;

    @FXML
    private TableView<Commande> table;

    @FXML
    private Button updateP;
  int idClient=1;
    PanierService panierService= new PanierService();
    CommandeService commandeService= new CommandeService();
    @FXML
    void initialize() throws SQLException {
        List<Commande> ls=commandeService.select();
       // for(Commande pn:ls) {
            //Panier panier = panierService.selectPanierById(pn.getIdPanier());
            //if (panier.getIdUser() == idClient) {}
                ObservableList<Commande> obs = FXCollections.observableArrayList(ls);
                table.setItems(obs);
                DateC.setCellValueFactory(new PropertyValueFactory<>("DateC"));
                adressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
                paiementCol.setCellValueFactory(new PropertyValueFactory<>("Mpaiement"));
                livraisonCol.setCellValueFactory(new PropertyValueFactory<>("Mlivraison"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                StatutCol.setCellValueFactory(new PropertyValueFactory<>("statut"));
                Mstatut.setCellFactory(column -> new BouttonModifierStatut(ls,table));
                DetailCol.setCellFactory(column -> new BouttonDetails(table));


    }

        }


