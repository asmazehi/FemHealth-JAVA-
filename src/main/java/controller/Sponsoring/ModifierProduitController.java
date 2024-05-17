package controller.Sponsoring;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Sponsoring.Produit;
import model.Sponsoring.Sponsor;
import service.Sponsoring.ProduitService;
import service.Sponsoring.SponsorService;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

public class ModifierProduitController {

    @FXML
    private TextField nomP;

    @FXML
    private TextField prixP;

    @FXML
    private TextField tauxRemiseP;

    @FXML
    private TextField categorieP;

    @FXML
    private TextField descriptionP;

    @FXML
    private ImageView imageView;

    @FXML
    private ChoiceBox<Sponsor> sponsorChoiceBox;

    @FXML
    private Text erreurNom;
    @FXML
    private Text erreurPrix;

    @FXML
    private Text erreurTauxRemise;

    @FXML
    private Text erreurCategorie;

    @FXML
    private Text erreurDescription;

    private ObservableList<Sponsor> sponsorList;
    private Produit produit;
    private AfficherProduitController afficherProduitController;

    public void setData(Produit produit, AfficherProduitController afficherProduitController) throws SQLException {
        this.produit = produit;
        this.afficherProduitController = afficherProduitController;
        nomP.setText(produit.getNom());
        prixP.setText(String.valueOf(produit.getPrix()));
        tauxRemiseP.setText(String.valueOf(produit.getTaux_remise()));
        categorieP.setText(produit.getCategorie());
        descriptionP.setText(produit.getDescription());

        File file = new File("C:/Users/Asma/Downloads/FemHealth/public/assets/uploads/product/" + produit.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
        } else {
            showAlert("Error", "Image not found");
        }

        // Populate sponsor choice box
        SponsorService sponsorService = new SponsorService();
        List<Sponsor> sponsors = sponsorService.select();
        sponsorList = FXCollections.observableArrayList();
        sponsorList.addAll(sponsors);
        sponsorChoiceBox.setItems(sponsorList);
        sponsorChoiceBox.setConverter(new StringConverter<Sponsor>() {
            @Override
            public String toString(Sponsor sponsor) {
                return sponsor != null ? sponsor.getNom() : "";
            }

            @Override
            public Sponsor fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    @FXML
    void executerModification() {
        String nom = nomP.getText();
        double prix = 0;
        double tauxRemise = 0;
        try {
            prix = Double.parseDouble(prixP.getText());
            tauxRemise = Double.parseDouble(tauxRemiseP.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir des valeurs numériques pour le prix et le taux de remise.");
            return;
        }
        String categorie = categorieP.getText();
        String description = descriptionP.getText();

        // Validation
        String erreurNomText = controleNom(nom);
        String erreurPrixText = controlePrix(prix);
        String erreurTauxRemiseText = controleTauxRemise(tauxRemise);
        String erreurCategorieText = controleCategorie(categorie);
        String erreurDescriptionText = controleDescription(description);

        // Affichage des erreurs
        erreurNom.setText(erreurNomText);
        erreurNom.setVisible(erreurNomText != null);

        erreurPrix.setText(erreurPrixText);
        erreurPrix.setVisible(erreurPrixText != null);

        erreurTauxRemise.setText(erreurTauxRemiseText);
        erreurTauxRemise.setVisible(erreurTauxRemiseText != null);

        erreurCategorie.setText(erreurCategorieText);
        erreurCategorie.setVisible(erreurCategorieText != null);

        erreurDescription.setText(erreurDescriptionText);
        erreurDescription.setVisible(erreurDescriptionText != null);

        if (erreurNomText != null || erreurPrixText != null || erreurTauxRemiseText != null || erreurCategorieText != null || erreurDescriptionText != null) {
            return;
        }

        produit.setNom(nom);
        produit.setPrix((float) prix);
        produit.setTaux_remise((int) tauxRemise);
        produit.setCategorie(categorie);
        produit.setDescription(description);
        produit.setSponsor(sponsorChoiceBox.getValue());

        ProduitService ps = new ProduitService();
        try {
            ps.update(produit);

            // Refresh data in AfficherProduitController
            afficherProduitController.refreshData(); // This line should refresh the table view data

            // Close the current stage (ModifierProduit)
            nomP.getScene().getWindow().hide();

        } catch (SQLException e) {
            showAlert("Erreur", e.getMessage());
        }
    }
    private String imagePath;
    @FXML
    void handleUploadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String targetDir = "C:/xampp8/htdocs/femHealthfinal/public/assets/uploads/product/";
                Path targetPath = Files.copy(file.toPath(), new File(targetDir + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                String absolutePath = targetPath.toAbsolutePath().toString();
                imageView.setImage(new Image("file:" + absolutePath));

                // Set the image path in the class-level variable
                imagePath = file.getName(); // Just store the filename without the path

                // Update the product's image path
                produit.setImage(imagePath);
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }
    }

    private String controleNom(String nom) {
        if (nom.isEmpty()) {
            return "Veuillez remplir le champ nom.";
        }
        return null; // Aucune erreur
    }

    private String controlePrix(double prix) {
        if (prix <= 0) {
            return "Le prix doit être supérieur à zéro.";
        }
        return null; // Aucune erreur
    }

    private String controleTauxRemise(double tauxRemise) {
        if (tauxRemise < 0 || tauxRemise > 100) {
            return "Le taux de remise doit être compris entre 0 et 100.";
        }
        return null; // Aucune erreur
    }

    private String controleCategorie(String categorie) {
        if (categorie.isEmpty()) {
            return "Veuillez remplir le champ catégorie.";
        }
        return null; // Aucune erreur
    }

    private String controleDescription(String description) {
        if (description.isEmpty()) {
            return "Veuillez remplir le champ description.";
        }
        return null; // Aucune erreur
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}