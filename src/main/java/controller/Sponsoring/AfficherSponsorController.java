package controller.Sponsoring;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sponsoring.Sponsor;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import service.Sponsoring.SponsorService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AfficherSponsorController {
    SponsorService ss = new SponsorService();
    ObservableList<Sponsor> obs;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Sponsor, String> nomS;
    @FXML
    private TableColumn<Sponsor, String> dureeS;
    @FXML
    private TableView<Sponsor> tableView;
    @FXML
    private Label welcomeLBL;
    private static boolean ajouterSponsorPageOpen = false;
    @FXML
    void supprimerSponsor(ActionEvent event) {
        Sponsor s = tableView.getSelectionModel().getSelectedItem();
        if (s != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce sponsor ?");
            alert.setContentText("Cette action est irréversible.");

            // Customize the button text to be in French
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.getButtonTypes().forEach(buttonType -> {
                if (buttonType.getButtonData().isCancelButton()) {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Non");
                } else {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Oui");
                }
            });

            // Show the confirmation dialog and handle the result
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    ss.delete(s.getId());
                    obs.remove(s);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @FXML
    void initialize() {
        try {
            List<Sponsor> list = ss.select();
            obs = FXCollections.observableArrayList(list);

            tableView.setItems(obs);
            nomS.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dureeS.setCellValueFactory(new PropertyValueFactory<>("duree_contrat"));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setData(String msg) {
        welcomeLBL.setText("Bienvenue chez FemHealth ");
    }

    @FXML
    void naviguerVersAjouterSponsor(ActionEvent event) {
        try {
            // Load the AjouterSponsor page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AjouterSponsor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un Sponsor");
            stage.setOnCloseRequest(e -> {
                ajouterSponsorPageOpen = false; // Reset the flag in the AfficherSponsorController
            });
            stage.show();

            ajouterSponsorPageOpen = true;

            // Close the current stage
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue AjouterSponsor.fxml: " + e.getMessage());
        }
    }


    public static void setAjouterSponsorPageOpen(boolean open) {
        ajouterSponsorPageOpen = open;
    }
    @FXML
    void modifierSponsor(ActionEvent event) {
        Sponsor selectedSponsor = tableView.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            try {
                // Load the ModifierSponsor page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/ModifierSponsor.fxml"));
                Parent root = loader.load();
                ModifierSponsorController controller = loader.getController();
                controller.setData(selectedSponsor, (Stage) tableView.getScene().getWindow());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modifier un Sponsor");
                stage.show();

            } catch (IOException e) {
                System.err.println("Erreur lors du chargement de la vue ModifierSponsor.fxml: " + e.getMessage());
            }
        }
    }
    public void refreshData() {
        try {
            List<Sponsor> list = ss.select();
            obs = FXCollections.observableArrayList(list);

            tableView.setItems(obs);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void genererPDF(ActionEvent event) {
        Sponsor selectedSponsor = tableView.getSelectionModel().getSelectedItem();
        if (selectedSponsor != null) {
            String filePath = "ContratSponsoring.pdf";
            StringBuilder content = new StringBuilder();

            content.append("Nom: ").append(selectedSponsor.getNom()).append("\n");
            content.append("Durée du contrat: ").append(selectedSponsor.getDuree_contrat()).append("\n");
            // Add other sponsor information as needed

            try {
                createPDF(filePath, selectedSponsor); // Pass the selected sponsor object
                showAlert("Succès", "Le fichier PDF a été généré avec succès.");

                // Open the PDF with the default application
                File file = new File(filePath);
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);

            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de la génération du PDF : " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un sponsor.");
        }
    }



    private void createPDF(String filePath, Sponsor sponsor) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
                float margin = 50;
                float fontSize = 12;
                float leading = 1.5f * fontSize;

                // Add logo
                PDImageXObject logoImage = PDImageXObject.createFromFile("src/main/java/controller/Sponsoring/Logo_ESPRIT.jpg", document);
                float logoWidth = (float) logoImage.getWidth() / 6;
                float logoHeight = (float) logoImage.getHeight() / 6;
                float logoX = pageWidth - logoWidth - margin; // Position X du coin supérieur gauche du logo
                float logoY = margin; // Position Y du coin supérieur gauche du logo
                contentStream.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight);

                // Add title
                addCenteredText(contentStream, "Contrat de sponsoring", pageWidth, pageHeight - margin, PDType1Font.HELVETICA_BOLD, 20, leading);

                // Add date
                addCenteredText(contentStream, "Fait à Tunis, le " + LocalDate.now(), pageWidth, pageHeight - margin - 2 * leading, PDType1Font.HELVETICA, fontSize, leading);

                // Add sponsors info
                addText(contentStream, "Entre les soussignés :", margin, pageHeight - margin - (logoHeight + 10), PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "La société FemHealth, domiciliée en Tunisie, représentée par l'équipe DevDivas, ci-après dénommée ", margin, pageHeight - margin - (logoHeight + 10) - leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "\"FemHealth\" .", margin, pageHeight - margin - (logoHeight + 10) - 2 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "Et " +sponsor.getNom() + ", domicilié en Tunisie, ci-après dénommé le \"Sponsor\",", margin, pageHeight - margin - (logoHeight + 10) - 3 * leading, PDType1Font.HELVETICA, fontSize, leading);

                // Add contrat details
                addText(contentStream, "Il est convenu et arrêté ce qui suit :", margin, pageHeight - margin - (logoHeight + 10) - 6 * leading, PDType1Font.HELVETICA_BOLD, fontSize, leading);
                addText(contentStream, "Article 1 : Objet du contrat", margin, pageHeight - margin - (logoHeight + 10) - 7 * leading, PDType1Font.HELVETICA_BOLD, fontSize, leading);
                addText(contentStream, "Le présent contrat a pour objet de définir les conditions de sponsoring entre le Sponsor et FemHealth.", margin, pageHeight - margin - (logoHeight + 10) - 8 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "Article 2 : Durée du contrat", margin, pageHeight - margin - (logoHeight + 10) - 9 * leading, PDType1Font.HELVETICA_BOLD, fontSize, leading);
                addText(contentStream, "Le présent contrat est conclu pour une durée de " + sponsor.getDuree_contrat() + " mois à compter de la date de signature.", margin, pageHeight - margin - (logoHeight + 10) - 10 * leading, PDType1Font.HELVETICA, fontSize, leading);

                // Add signatures
                addText(contentStream, "Fait en deux exemplaires originaux, à Tunis", margin, pageHeight - margin - (logoHeight + 10) - 13 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "Le Sponsor, " + sponsor.getNom(), margin, pageHeight - margin - (logoHeight + 10) - 14 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "Signature", margin, pageHeight - margin - (logoHeight + 10) - 15 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "FemHealth", margin, pageHeight - margin - (logoHeight + 10) - 16 * leading, PDType1Font.HELVETICA, fontSize, leading);
                addText(contentStream, "Signature", margin, pageHeight - margin - (logoHeight + 10) - 17 * leading, PDType1Font.HELVETICA, fontSize, leading);
            }

            // Save the document
            document.save(filePath);

            // Open the PDF
            File file = new File(filePath);
            Desktop.getDesktop().open(file);
        }
    }



    private void addCenteredText(PDPageContentStream contentStream, String text, float pageWidth, float yPosition, PDFont font, float fontSize, float leading) throws IOException {
        float stringWidth = font.getStringWidth(text) / 1000 * fontSize;
        float startX = (pageWidth - stringWidth) / 2;
        contentStream.setFont(font, fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, yPosition);
        contentStream.showText(text);
        contentStream.newLineAtOffset(0, -leading);
        contentStream.endText();
    }

    private void addText(PDPageContentStream contentStream, String text, float xPosition, float yPosition, PDFont font, float fontSize, float leading) throws IOException {
        contentStream.setFont(font, fontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.newLineAtOffset(0, -leading);
        contentStream.endText();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }
    @FXML
    void voirStat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/statSponsor.fxml"));
            Parent root = loader.load();
            StatController controller = loader.getController();
            controller.setData(); // You may pass any necessary data to the StatController here
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Statistiques des Sponsors");
            stage.show();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue statSponsor.fxml: " + e.getMessage());
        }
    }


}
