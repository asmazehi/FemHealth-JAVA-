package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.events.Type;
import service.events.TypeC;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterTypeB {

    @FXML
    private TextField typeTF;

    @FXML
    void AfficherTypeB(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherTypeB.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de la nouvelle vue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load EventAfficherTypeB.fxml");
            alert.show();
        }
    }

    @FXML
    void AjouterTypeB(ActionEvent event) {
        String type = typeTF.getText();

        // Vérifier si le champ de saisie est vide
        if (type.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez saisir un type valide (une chaîne de caractères non vide)");
            alert.show();
        } else if (!type.matches("[a-zA-Z]+")) { // Vérifier si le type ne contient que des lettres alphabétiques
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez saisir un type valide (une chaîne de caractères alphabétiques uniquement)");
            alert.show();
        } else {
            // Ajouter le type
            Type t = new Type(type);
            TypeC tc = new TypeC();
            try {
                tc.add(t);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Type ajouté");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }
    @FXML
    void goBack(ActionEvent event) {
        try {
            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Load and show the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/BaseAdmin.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ajouterTypeB(ActionEvent event) {
        try {
            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Load and show the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AjouterTypeB.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
