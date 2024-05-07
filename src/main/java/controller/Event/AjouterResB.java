package controller.Event;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.events.Evenement;
import model.events.Reservation;
import service.events.EvenementC;
import service.events.ReservationC;

import java.io.*;
import java.sql.SQLException;

public class AjouterResB {

    @FXML
    private TextField id_evenement_idTF;

    @FXML
    private ComboBox<String> mode_paiementCB;

    @FXML
    private TextField statut_paiementTF;
    @FXML
    private Label eventIdLabel;

    private int eventId;

    private EvenementC evenementService; // Instance of EvenementC

    public AjouterResB() {
        evenementService = new EvenementC(); // Initialize EvenementC instance
    }

    @FXML
    void AfficherResB(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherResB.fxml"));
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
            alert.setContentText("Failed to load EventAfficherResB.fxml");
            alert.show();
        }
    }


    @FXML
    void AjouterResB(ActionEvent event) {
        String statut_paiement = "en attente";
        String mode_paiement = mode_paiementCB.getValue();

        // Tous les champs sont remplis, nous pouvons procéder à l'ajout
        Reservation r = new Reservation();
        Evenement evenement = null;
        ReservationC rc = new ReservationC();

        try {
            // Vérifier si l'événement existe (you might need to pass or retrieve the eventId from another source)
            evenement = evenementService.getEventInformationFromDatabase(eventId); // Call getEventInformationFromDatabase from EvenementC
            if (evenement != null) {
                r.setId_evenement_id(evenement);
                r.setMode_paiement(mode_paiement);

                rc.add(r);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Réservation ajoutée");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Cet événement n'existe pas");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    public void initData(int eventId) {
        this.eventId = eventId;
        // Update the label to display the event ID
        eventIdLabel.setText("Event ID: " + eventId);
    }
    @FXML
    void goBack(ActionEvent event) {
        try {
            // Load the previous page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageEventF.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene and show the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error if loading the FXML fails
        }
    }
    @FXML
    void generatePDF(ActionEvent event) throws SQLException {
        // Retrieve the information of the event that was clicked on
        Evenement eventClicked = evenementService.getEventInformationFromDatabase(eventId);

        // Create a new PDF document
        Document document = new Document();
        try {
            // Create a FileChooser to allow the user to choose the file name and location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

            if (file != null) {
                // Open the document to start adding content
                PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();

                // Set title font and color
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
                titleFont.setColor(255, 105, 180); // Pink color

                // Add title message
                Paragraph title = new Paragraph("Vous avez réservé pour cet événement:", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n")); // Add some space after the title

                // Add event information to the PDF
                document.add(new Paragraph("Event ID: " + eventClicked.getId()));
                document.add(new Paragraph("Event Name: " + eventClicked.getNom()));
                document.add(new Paragraph("La localisation: " + eventClicked.getLocalisation()));
                document.add(new Paragraph("Montant: " + eventClicked.getMontant()));
                document.add(new Paragraph("La date de début de l'event: " + eventClicked.getDateDebut()));
                document.add(new Paragraph("La date de Fin de l'event: " + eventClicked.getDateFin()));

                // Add event image if available
                if (eventClicked.getImage() != null && !eventClicked.getImage().isEmpty()) {
                    Image image = Image.getInstance(eventClicked.getImage());
                    document.add(image);
                }

                // Add more event information as needed

                // Close the document
                document.close();

                // Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Generated");
                alert.setContentText("PDF generated successfully!");
                alert.show();
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Handle any exceptions that occur during PDF generation
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to generate PDF");
            alert.show();
        }
    }


}
