package controller.Sponsoring;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sponsoring.Produit;
import service.Sponsoring.ProduitService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherProduitFrontController {
    ProduitService ps = new ProduitService();
    ObservableList<Produit> obs;

    @FXML
    private TableColumn<Produit, String> nomP;
    @FXML
    private TableColumn<Produit, Double> prixP;
    @FXML
    private TableColumn<Produit, Double> tauxRemiseP;
    @FXML
    private TableColumn<Produit, String> categorieP;
    @FXML
    private TableColumn<Produit, String> descriptionP;
    @FXML
    private TableColumn<Produit, String> imagePathP;
    @FXML
    private TableView<Produit> tableView;
    @FXML
    private Label welcomeLBL;

    private static boolean ajouterProduitPageOpen = false;


    @FXML
    void initialize() {
        try {
            List<Produit> list = ps.select();
            obs = FXCollections.observableArrayList(list);

            tableView.setItems(obs);
            nomP.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prixP.setCellValueFactory(new PropertyValueFactory<>("prix"));
            tauxRemiseP.setCellValueFactory(new PropertyValueFactory<>("taux_remise"));
            categorieP.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            descriptionP.setCellValueFactory(new PropertyValueFactory<>("description"));
            imagePathP.setCellValueFactory(new PropertyValueFactory<>("image"));

            // Custom cell factory for displaying images
            imagePathP.setCellFactory(column -> {
                return new TableCell<Produit, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);

                        if (empty || imagePath == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            File file = new File(imagePath);
                            if (file.exists()) {
                                Image image = new Image(file.toURI().toString());
                                imageView.setImage(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                setGraphic(imageView);
                                setText(null);
                            } else {
                                setText("Image not found");
                            }
                        }
                    }
                };
            });

=======
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.Sponsoring.Produit;
import service.Sponsoring.ProduitService;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AfficherProduitFrontController {

    @FXML
    private FlowPane produitFlowPane;

    private ProduitService ps = new ProduitService();

    @FXML
    public void initialize() {
        try {
            List<Produit> produitList = ps.select();

            for (Produit produit : produitList) {
                AnchorPane card = createProduitCard(produit);
                produitFlowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }


    @FXML
    void supprimerProduit(ActionEvent event) {
        Produit p = tableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce produit ?");
            alert.setContentText("Cette action est irréversible.");

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.getButtonTypes().forEach(buttonType -> {
                if (buttonType.getButtonData().isCancelButton()) {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Non");
                } else {
                    ((Button) alert.getDialogPane().lookupButton(buttonType)).setText("Oui");
                }
            });

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    ps.delete(p.getId());
                    obs.remove(p);

                    // Delete the image file
                    String imagePath = p.getImage();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists() && !imageFile.delete()) {
                            System.err.println("Failed to delete image file: " + imageFile.getAbsolutePath());
                        } else {
                            System.out.println("Image file deleted successfully: " + imageFile.getAbsolutePath());
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error deleting product: " + e.getMessage());
                }
            }
        }
    }


    @FXML
    void naviguerVersAjouterProduit(ActionEvent event) {
        try {
            // Load the AjouterProduit page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AjouterProduit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un Produit");
            stage.setOnCloseRequest(e -> {
                ajouterProduitPageOpen = false; // Reset the flag in the AfficherProduitController
            });
            stage.show();

            ajouterProduitPageOpen = true;

            // Close the current stage
            Stage currentStage = (Stage) tableView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Error loading AjouterProduit.fxml: " + e.getMessage());
        }
    }
    public void refreshData() {
        try {
            List<Produit> list = ps.select();
            obs.setAll(list);
        } catch (SQLException e) {
            System.err.println("Error refreshing data: " + e.getMessage());
        }
    private AnchorPane createProduitCard(Produit produit) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 300); // Taille fixe pour chaque carte

        // Ajouter un contour à la carte
        card.setStyle("-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 5px;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(15);
        imageView.setLayoutY(15);
        imageView.getStyleClass().add("produit-image");

        File file = new File(produit.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

        Label nomLabel = new Label(produit.getNom());
        nomLabel.setLayoutX(15);
        nomLabel.setLayoutY(180);
        nomLabel.getStyleClass().add("produit-nom");

        Label marqueLabel = new Label("Marque: " + produit.getSponsor().getNom());
        marqueLabel.setLayoutX(15);
        marqueLabel.setLayoutY(200);
        marqueLabel.getStyleClass().add("produit-marque");

        double newPrice = produit.getPrix() - (produit.getPrix() * produit.getTaux_remise() / 100);

        Label prixLabel = new Label("Prix: " + String.format("%.2f", produit.getPrix()) + " DT (" + produit.getTaux_remise() + "% de remise)");
        prixLabel.setLayoutX(15);
        prixLabel.setLayoutY(220);
        prixLabel.getStyleClass().add("produit-prix");

        Label newPriceLabel = new Label("Nouveau Prix: " + String.format("%.2f", newPrice) + " DT");
        newPriceLabel.setLayoutX(15);
        newPriceLabel.setLayoutY(240);
        newPriceLabel.setTextFill(Color.BLACK);
        newPriceLabel.getStyleClass().add("produit-nouveau-prix");

        card.getChildren().addAll(imageView, nomLabel, marqueLabel, prixLabel, newPriceLabel);
        return card;
    }


}
