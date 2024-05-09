package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.events.Type;
import service.events.TypeC;

import java.sql.SQLException;

public class ModifierTypeB {

    @FXML
    private TextField typeTF;

    private Type typeToModify;

    // Méthode pour définir le type à modifier
    public void setTypeToModify(Type type) {
        this.typeToModify = type;
        // Display the name of the type in the text field for modification
        typeTF.setText(type.getType());
    }

    @FXML
    void ModifierTypeB(ActionEvent event) {
        // Check if the type to modify is defined
        if (typeToModify != null) {
            String newType = typeTF.getText();
            // Check if the new type field is not empty
            if (!newType.isEmpty()) {
                TypeC typeC = new TypeC();
                try {
                    // Update the type in the database
                    typeToModify.setType(newType);
                    typeC.update(typeToModify);
                    // Display a success message
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Type modifié avec succès");
                    alert.show();
                    // Close the modification window
                    Stage stage = (Stage) typeTF.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    // Handle database errors
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Erreur lors de la modification du type : " + e.getMessage());
                    alert.show();
                }
            } else {
                // Display a message if the new type field is empty
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Veuillez saisir un nouveau type");
                alert.show();
            }
        } else {
            // Display a message if the type to modify is not defined
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Type à modifier non défini");
            alert.show();
        }
    }

    public void initData(Type type) {
        setTypeToModify(type);
    }
}