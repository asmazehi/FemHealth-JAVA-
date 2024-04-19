package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterEventB {

    @FXML
    private TextField dateDebutTF;

    @FXML
    private TextField dateFinTF;

    @FXML
    private TextField imageTF;

    @FXML
    private TextField localisationTF;

    @FXML
    private TextField montantTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField type_idTF;



    @FXML
    private String NFTImageTF;    @FXML
    private ImageView imageView;

    @FXML
    void AfficherEventB(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherEventB.fxml"));
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
            alert.setContentText("Failed to load AfficherEventB.fxml");
            alert.show();
        }
    }


    @FXML
    void AjouterEventB(ActionEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Vérification des champs vides et du type
        if (nomTF.getText().isEmpty() || dateDebutTF.getText().isEmpty() || dateFinTF.getText().isEmpty() ||
                imageTF.getText().isEmpty() || localisationTF.getText().isEmpty() || montantTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        } else if (!isFloat(montantTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Montant doit être un nombre décimal");
            alert.show();
        } else if (!isValidDate(dateDebutTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Date de début invalide (format : dd/MM/yyyy)");
            alert.show();
        } else if (!isValidDate(dateFinTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Date de fin invalide (format : dd/MM/yyyy)");
            alert.show();
        } else if (!isString(nomTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Nom doit être une chaîne de caractères");
            alert.show();
        } else if (!isString(localisationTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Localisation doit être une chaîne de caractères");
            alert.show();
        } else {
            // Parse the type_id from TextField
            int type_idValue = Integer.parseInt(type_idTF.getText());

            // Fetch the Type object based on the type_idValue
            Type type_id = null;
            try {
                type_id = EvenementC.getType_id(type_idValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Check if the Type object exists
            if (type_id != null) {
                try {
                    String nom = nomTF.getText();
                    String localisation = localisationTF.getText();
                    Date dateDebut = sdf.parse(dateDebutTF.getText());
                    Date dateFin = sdf.parse(dateFinTF.getText());
                    String image = imageTF.getText();
                    float montant = Float.parseFloat(montantTF.getText());

                    // Create Evenement object with fetched Type
                    Evenement ev = new Evenement(type_id, nom, dateDebut, dateFin, image, localisation, montant);

                    // Call the add method from EvenementC to add the event
                    EvenementC ec = new EvenementC();

                    ec.add(ev);

                    // Show success alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Event added successfully");
                    alert.show();
                } catch (ParseException | SQLException e) {
                    // Handle parsing or database error
                    e.printStackTrace();
                    // Show error alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to add event");
                    alert.show();
                }
            } else {
                // Show warning if Type object does not exist
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setContentText("Type non valide");
                alert.show();
            }
        }
    }

    // Méthode pour vérifier si une chaîne peut être convertie en float
    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Méthode pour vérifier si une chaîne est de type chaîne de caractères
    private boolean isString(String str) {
        return str.matches("[a-zA-Z]+");
    }

    // Méthode pour vérifier si une chaîne peut être convertie en date
    private boolean isValidDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }




    @FXML
    void handleUploadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (.jpg, *.png)", ".jpg", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Handle the selected file (e.g., store the file path or display it)
            String imagePath = file.getAbsolutePath();
            // You can then use imagePath to load the image into an ImageView or do any other processing
        }
    }
}
