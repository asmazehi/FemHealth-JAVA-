package controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.events.Type;
import service.events.TypeC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherTypeB {

    @FXML
    private TableView<Type> tableView;

    @FXML
    private TableColumn<Type, String> typecol;

    TypeC tc = new TypeC();
    ObservableList<Type> obs;

    @FXML
    void supprimerTypeB(ActionEvent event) {
        try {
            Type t = tableView.getSelectionModel().getSelectedItem();
            tc.delete(t.getId());
            obs.remove(t);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierTypeB(ActionEvent event) {
        try {
            Type selectedType = tableView.getSelectionModel().getSelectedItem();
            if (selectedType != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/ModifierTypeB.fxml"));
                Parent root = loader.load();

                ModifierTypeB controller = loader.getController();
                controller.initData(selectedType);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun type sélectionné");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un type à modifier.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            List<Type> list = tc.select();
            obs = FXCollections.observableArrayList(list);
            tableView.setItems(obs);
            typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
