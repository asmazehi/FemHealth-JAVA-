package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;
import service.events.TypeC;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AjouterEventB {

    @FXML
    private DatePicker dateDebutDP;

    @FXML
    private DatePicker dateFinDP;

    @FXML
    private TextField localisationTF;

    @FXML
    private TextField montantTF;

    @FXML
    private TextField nomTF;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        // Populate the ComboBox with type names fetched from the database
        try {
            List<String> typeNames = new TypeC().getAllTypeNames();
            typeCB.getItems().addAll(typeNames);
        } catch (SQLException e) {
            showAlert("Error", "Failed to fetch type names from the database");
            e.printStackTrace();
        }
    }

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
    void AjouterEventB(ActionEvent event ) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Vérification des champs vides et du type
        if (nomTF.getText().isEmpty() || dateDebutDP.getValue() == null || dateFinDP.getValue() == null || localisationTF.getText().isEmpty() || montantTF.getText().isEmpty() || typeCB.getValue() == null) {
            showAlert("Attention", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérification du format de date
        if (!isValidDateFormat(dateDebutDP) || !isValidDateFormat(dateFinDP)) {
            showAlert("Attention", "Le format de date doit être : dd/MM/yyyy");
            return;
        }

        // Vérification de l'ordre des dates
        LocalDate dateDebut = dateDebutDP.getValue();
        LocalDate dateFin = dateFinDP.getValue();
        if (dateFin.isBefore(dateDebut)) {
            showAlert("Attention", "La date de fin doit être ultérieure à la date de début.");
            return;
        }

        // Fetch the Type object based on the selected type name
        String selectedTypeName = typeCB.getValue();
        Type type = null;
        try {
            type = new TypeC().selectByName(selectedTypeName);
        } catch (SQLException e) {
            showAlert("Error", "Failed to fetch type from the database");
            return;
        }

        // Check if the Type object exists
        if (type != null) {
            try {
                String nom = nomTF.getText();
                String localisation = localisationTF.getText();
                Date dateDebutParsed = sdf.parse(dateDebut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                Date dateFinParsed = sdf.parse(dateFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                float montant = Float.parseFloat(montantTF.getText());

                // Create Evenement object with fetched Type
                Evenement ev = new Evenement(type, nom, dateDebutParsed, dateFinParsed, "", localisation, montant);

                // Get the image file name
                String imageFileName = imageView.getImage().getUrl().substring(imageView.getImage().getUrl().lastIndexOf('/') + 1);

                // Set the image path for the event
                ev.setImage(imageFileName);

                // Call the add method from EvenementC to add the event
                EvenementC ec = new EvenementC();
                ec.add(ev);

                // Show success alert
                showAlert("Success", "Event added successfully");
            } catch (ParseException | SQLException e) {
                // Handle parsing or database error
                showAlert("Error", "Failed to add event");
            }
        } else {
            // Show warning if Type object does not exist
            showAlert("Attention", "Type non valide");
        }
    }

    // Méthode pour vérifier le format de date
    private boolean isValidDateFormat(DatePicker datePicker) {
        try {
            datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleUploadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                ///////////////////////hedhaa
                String targetDir = "C:/xampp8/htdocs/femHealthfinal/public/assets/uploads/events/";
                Path targetPath = Files.copy(file.toPath(), new File(targetDir + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(targetPath.toUri().toString()); // Load the image from the copied file
                imageView.setImage(new Image("file:" + targetPath));
                imageView.setImage(image);
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }
    }
}
