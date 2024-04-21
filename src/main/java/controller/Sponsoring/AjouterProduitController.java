package controller.Sponsoring;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Sponsoring.Produit;
import model.Sponsoring.Sponsor;
import service.Sponsoring.ProduitService;
import service.Sponsoring.SponsorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AjouterProduitController {

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

    @FXML
    public void initialize() throws SQLException {
        sponsorList = FXCollections.observableArrayList();
        SponsorService sponsorService = new SponsorService();
        List<Sponsor> sponsors = sponsorService.select();
        sponsorList.addAll(sponsors);
        sponsorChoiceBox.setItems(sponsorList);
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        String nom = nomP.getText();
        String prixText = prixP.getText();
        String tauxRemiseText = tauxRemiseP.getText();
        String categorie = categorieP.getText();
        String description = descriptionP.getText();

        // Validation
        String erreurNomText = controleNom(nom);
        String erreurPrixText = controlePrix(prixText);
        String erreurTauxRemiseText = controleTauxRemise(tauxRemiseText);
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

        // Si des erreurs sont présentes, arrêter la méthode
        if (erreurNomText != null || erreurPrixText != null || erreurTauxRemiseText != null || erreurCategorieText != null || erreurDescriptionText != null) {
            return;
        }

        // Conversion des valeurs
        float prix = Float.parseFloat(prixText);
        int tauxRemise = Integer.parseInt(tauxRemiseText);

        Sponsor sponsor = sponsorChoiceBox.getValue();

        Produit produit = new Produit(0, nom, prix, tauxRemise, categorie, "", description, sponsor);
        ProduitService produitService = new ProduitService();
        try {
            produitService.add(produit);
            showAlert("Success", "Produit ajouté avec succès.");
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout du produit : " + e.getMessage());
        }
    }


    @FXML
    void handleUploadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String targetDir = "src/main/resources/img/";
                Path targetPath = Files.copy(file.toPath(), new File(targetDir + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                imageView.setImage(new Image("file:" + targetDir + file.getName()));
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }
    }

    private String controleNom(String nom) {
        if (nom.isEmpty()) {
            return "Le nom ne peut pas être vide.";
        } else if (nom.length() > 20) {
            return "Le nom ne peut pas dépasser 20 caractères.";
        }
        return null; // pas d'erreur
    }

    private String controlePrix(String prixText) {
        if (prixText.isEmpty()) {
            return "Le prix ne peut pas être vide.";
        }
        try {
            float prix = Float.parseFloat(prixText);
            if (prix < 0) {
                return "Le prix doit être positif.";
            }
        } catch (NumberFormatException e) {
            return "Le prix doit être un nombre valide.";
        }
        return null; // pas d'erreur
    }

    private String controleTauxRemise(String tauxRemiseText) {
        if (tauxRemiseText.isEmpty()) {
            return "Le taux de remise ne peut pas être vide.";
        }
        try {
            int tauxRemise = Integer.parseInt(tauxRemiseText);
            if (tauxRemise < 0 || tauxRemise > 100) {
                return "Le taux de remise doit être compris entre 0 et 100.";
            }
        } catch (NumberFormatException e) {
            return "Le taux de remise doit être un nombre entier valide.";
        }
        return null; // pas d'erreur
    }

    private String controleCategorie(String categorie) {
        if (categorie.isEmpty()) {
            return "La catégorie ne peut pas être vide.";
        } else if (!Arrays.asList("Sport", "Nutrition", "Santé Mentale").contains(categorie)) {
            return "La catégorie doit être 'Sport', 'Nutrition' ou 'Santé Mentale'.";
        }
        return null; // pas d'erreur
    }

    private String controleDescription(String description) {
        if (description.isEmpty()) {
            return "La description ne peut pas être vide.";
        }
        return null; // pas d'erreur
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nomP.clear();
        prixP.clear();
        tauxRemiseP.clear();
        categorieP.clear();
        descriptionP.clear();
        imageView.setImage(null);
    }
    @FXML
    void gobackAfficherProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherProduit.fxml"));
            Parent root = loader.load();
            AfficherProduitController controller = loader.getController();
            controller.initialize(); // You might need to initialize the controller if it has an initialize method
            Stage stage = (Stage) nomP.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors de la navigation : " + e.getMessage());
        }
    }
}

