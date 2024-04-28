package controller.front.Ecommerce;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.Ecommerce.PanierItem;

public class BouttonModifierQuantiteController extends TableCell<PanierItem, Void> {

    private Spinner<Integer> Mquantite;
    private TableView<PanierItem> tableView;

    public BouttonModifierQuantiteController(ObservableList<PanierItem> panierItems, TableView<PanierItem> tableView) {
        this.tableView = tableView;
        this.Mquantite = new Spinner<>(1, Integer.MAX_VALUE, 1, 1);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
           // PanierItem panierItem = getTableView().getItems().get(getIndex());
            //Mquantite.getValueFactory().setValue(panierItem.getQuantite());

            // Retirer l'ancien écouteur de propriété de valeur pour éviter les fuites de mémoire
            //Mquantite.valueProperty().removeListener(this::onValueChanged);

            // Ajouter un nouvel écouteur de propriété de valeur
            //Mquantite.valueProperty().addListener(this::onValueChanged);

            setGraphic(Mquantite);
        }
    }

    private void onValueChanged(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        PanierItem panierItem = getTableView().getItems().get(getIndex());
        panierItem.setQuantite(newValue);
        tableView.getItems().set(getIndex(), panierItem);
    }
}
