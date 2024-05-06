package controller.front.Ecommerce;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.Ecommerce.Commande;
import model.Ecommerce.Panier;
import controller.front.Ecommerce.*;
import model.Ecommerce.PanierItem;
import service.Ecommerce.CommandeService;

import java.sql.SQLException;
import java.util.List;



public class BouttonModifierStatut extends TableCell<Commande, Void> {

    private Button MBoutton;
    private ObservableList<PanierItem> observableList; // Référence à l'ObservableList
    private TableView<Commande> tableView; // Référence à la TableView
    private CommandeService commandeService = new CommandeService();

    public BouttonModifierStatut(CommandeService commandeService) {
        this.commandeService = commandeService;
        this.MBoutton = new Button("Annuler");
        this.MBoutton.setOnAction(event -> {
            Commande commande = getTableView().getItems().get(getIndex());
            try {
                commande.setStatut("annuler");
                commandeService.update(commande);
                tableView.getItems().setAll(commandeService.select());
            } catch (SQLException e) {
                // Afficher une alerte en cas d'échec de suppression
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Deletion Failed");
                alert.setContentText("Failed to delete item from the cart!");
                alert.showAndWait();
            }
        });
    }

    /*@Override
    protected void updateItem(Void item, boolean empty) {
        Commande commande = getTableView().getItems().get(getIndex());
        super.updateItem(item, empty);
        if (empty || commande.getStatut().equals("annuler") || commande.getStatut().equals("Terminé")) {
            setGraphic(null);
        } else {
            setGraphic(MBoutton);
        }*/

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(MBoutton);
        }
    }

}



