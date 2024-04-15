package controller.Event;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.events.Type;
import service.events.TypeC;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherTypeB {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Type> tableView;

    @FXML
    private TableColumn<Type, String> typecol;

    @FXML
    private Label welcomeBL;
TypeC tc = new TypeC();
    ObservableList<Type> obs;
    @FXML
    void supprimerTypeB(ActionEvent event) {
        try {
            Type t=tableView.getSelectionModel().getSelectedItem();
            tc.delete(t.getId());
            obs.remove(t);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
