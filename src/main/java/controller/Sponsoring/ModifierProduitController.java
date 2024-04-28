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

        // Load image
        Image image = new Image(Paths.get(produit.getImage()).toUri().toString());
        imageView.setImage(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Populate sponsor choice box
        SponsorService sponsorService = new SponsorService();
        List<Sponsor> sponsors = sponsorService.select();
        sponsorList = FXCollections.observableArrayList(sponsors); // Initialize sponsorList
        sponsorChoiceBox.setItems(sponsorList);
        sponsorChoiceBox.setValue(produit.getSponsor());
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
            // Handle parsing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir des valeurs numériques pour le prix et le taux de remise.");
            alert.show();
            return;
        }
        String categorie = categorieP.getText();
        String description = descriptionP.getText();

        // Vérifier la saisie
        String erreurNomText = controleNom(nom);
        String erreurPrixText = controlePrix(prix);
        String erreurTauxRemiseText = controleTauxRemise(tauxRemise);
        String erreurCategorieText = controleCategorie(categorie);
        String erreurDescriptionText = controleDescription(description);

        // Afficher ou masquer les messages d'erreur
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
            return; // Il y a des erreurs, ne pas continuer
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
            afficherProduitController.refreshData();

            // Close the current stage (ModifierProduit)
            Stage modifierProduitStage = (Stage) nomP.getScene().getWindow();
            modifierProduitStage.close();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.show();
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

    @FXML
    void handleUploadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String targetDir = "src/main/resources/img/";
                Path targetPath = Files.copy(file.toPath(), new File(targetDir + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Delete old image if it exists
                if (produit.getImage() != null && !produit.getImage().isEmpty()) {
                    Files.deleteIfExists(Paths.get(produit.getImage()));
                }

                produit.setImage(targetPath.toString());
                imageView.setImage(new Image("file:" + targetDir + file.getName()));
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
