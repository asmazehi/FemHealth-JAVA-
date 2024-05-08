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
        // Affichez le nom du type dans le champ de texte pour la modification
        typeTF.setText(type.getType());
    }

    @FXML
    void ModifierTypeB(ActionEvent event) {
        // Vérifiez si le type à modifier est défini
        if (typeToModify != null) {
            String newType = typeTF.getText();
            // Vérifiez si le champ du nouveau type n'est pas vide
            if (!newType.isEmpty()) {
                TypeC typeC = new TypeC();
                try {
                    // Mettez à jour le type dans la base de données
                    typeToModify.setType(newType);
                    typeC.update(typeToModify);
                    // Affichez un message de succès
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Type modifié avec succès");
                    alert.show();
                    // Fermez la fenêtre de modification
                    Stage stage = (Stage) typeTF.getScene().getWindow();
                    stage.close();
                } catch (SQLException e) {
                    // Gérez les erreurs de base de données
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Erreur lors de la modification du type : " + e.getMessage());
                    alert.show();
                }
            } else {
                // Affichez un message si le champ du nouveau type est vide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Veuillez saisir un nouveau type");
                alert.show();
            }
        } else {
            // Affichez un message si le type à modifier n'est pas défini
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Type à modifier non défini");
            alert.show();
        }
    }

    public void initData(Type type) {
        Type selectedType = type;
        typeTF.setText(selectedType.getType()); // Set the type name to the TextField
    }
}