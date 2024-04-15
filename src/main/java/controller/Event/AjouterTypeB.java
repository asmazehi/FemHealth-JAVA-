package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.events.Type;
import service.events.TypeC;

import java.sql.SQLException;

public class AjouterTypeB {

    @FXML
    private TextField typeTF;

    @FXML
    void AfficherTypeB(ActionEvent event) {

    }

    @FXML
    void AjouterTypeB(ActionEvent event) {
String type=typeTF.getText();
Type t = new Type ( type ) ;
        TypeC tc = new TypeC();
        try {
            tc.add(t);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("success");
            alert.setContentText("type ajouté");
            alert.show();
        } catch (SQLException e) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Erreur");
alert.setContentText(e.getMessage());
alert.show();
        }
    }

}
