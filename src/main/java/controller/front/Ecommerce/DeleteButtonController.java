package controller.front.Ecommerce;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import model.Ecommerce.PanierItem;
import service.Ecommerce.LignepanierService;

import java.sql.SQLException;

public class DeleteButtonController extends TableCell<PanierItem, Void> {

    private  Button deleteButton;
    private  ObservableList<PanierItem> observableList; // Référence à l'ObservableList
         LignepanierService lignepanierService = new LignepanierService();

    public DeleteButtonController(ObservableList<PanierItem> observableList) {
        this.deleteButton = new Button("Delete");
        this.observableList = observableList; // Initialisation de l'ObservableList
        this.deleteButton.setOnAction(event -> {
            PanierItem item = getTableView().getItems().get(getIndex());
            try {
                lignepanierService.delete(item.getIdpanier(), item.getIdproduit());
                getTableView().getItems().remove(item);
                observableList.remove(item); // Suppression de l'élément de l'ObservableList
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


    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(deleteButton);
        }
    }
}
