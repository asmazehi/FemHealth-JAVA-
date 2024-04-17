package controller.Sponsoring;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Sponsoring.Produit;
import model.Sponsoring.Sponsor;
import service.Sponsoring.ProduitService;
import service.Sponsoring.SponsorService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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

    private String NFTImageTF;

    @FXML
    private ChoiceBox<Sponsor> sponsorChoiceBox;

    private ObservableList<Sponsor> sponsorList;

    @FXML
    public void initialize() {
        sponsorList = FXCollections.observableArrayList();
        SponsorService sponsorService = new SponsorService();
        try {
            List<Sponsor> sponsors = sponsorService.select();
            sponsorList.addAll(sponsors);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des sponsors : " + e.getMessage());
        }
        sponsorChoiceBox.setItems(sponsorList);
    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        String nom = nomP.getText();
        float prix = Float.parseFloat(prixP.getText());
        int taux_remise = Integer.parseInt(tauxRemiseP.getText());
        String categorie = categorieP.getText();
        String description = descriptionP.getText();

        if (NFTImageTF == null || NFTImageTF.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner une image.");
            return;
        }

        Sponsor sponsor = sponsorChoiceBox.getValue();

        Produit produit = new Produit(0, nom, prix, taux_remise, categorie, NFTImageTF, description, sponsor);
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
    void handleUploadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String targetDir = "src/main/resources/img/";
                Path targetPath = Files.copy(file.toPath(), new File(targetDir + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                NFTImageTF = targetPath.toString();
                imageView.setImage(new Image("file:" + targetDir + file.getName()));
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'upload de l'image : " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
}
