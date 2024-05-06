package controller.front.Ecommerce;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.Ecommerce.Commande;
import model.Ecommerce.PanierItem;
import service.Ecommerce.CommandeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BouttonDetails extends TableCell<Commande, Void> {
    private Button DetailsBoutton;
    private ObservableList<PanierItem> observableList; // Référence à l'ObservableList
    private TableView<Commande> tableView; // Référence à la TableView
    private CommandeService commandeService = new CommandeService();

        public BouttonDetails(TableView<Commande> tableView) {
            this.tableView = tableView; // Initialisation de la TableView
            this.DetailsBoutton = new Button("Details");

            this.DetailsBoutton.setOnAction(event -> {
                Commande commande = getTableView().getItems().get(getIndex());
                try {
                    int idp = commande.getIdPanier();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherCommande.fxml"));
                    Parent root = loader.load();
                    AfficherCommande controller = loader.getController();
                    controller.initialize(idp);
                    System.out.println(idp + "///// hethii ba3eddd");
                    DetailsBoutton.getScene().setRoot(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(DetailsBoutton);
            }
        }
    }



