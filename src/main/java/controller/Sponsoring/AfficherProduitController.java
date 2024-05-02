package controller.Sponsoring;

import javafx.beans.property.SimpleStringProperty;
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
import model.Sponsoring.Sponsor;
import service.Sponsoring.ProduitService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AfficherProduitController {
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
    private TableColumn<Produit, String> sponsor;
    @FXML
    private TableView<Produit> tableView;
    @FXML
    private Label welcomeLBL;
    private static boolean ajouterProduitPageOpen = false;
    @FXML
    private TextField searchField;

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
            sponsor.setCellValueFactory(cellData -> {
                Sponsor sponsor = cellData.getValue().getSponsor();
                return new SimpleStringProperty(sponsor != null ? sponsor.getNom() : "");
            });

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

            // Listener for search field to filter results
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    refreshData(); // Reset the table to show all products when the search field is cleared
                } else {
                    search();
                }
            });

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

    @FXML
    void modifierProduit(ActionEvent event) {
        Produit selectedProduit = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                // Correctly set the resource path
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/ModifierProduit.fxml"));
                if (loader.getLocation() == null) {
                    System.err.println("Error loading ModifierProduit.fxml: File not found.");
                    return;
                }
                Parent root = loader.load();

                // Get the controller and set the selectedProduit
                ModifierProduitController controller = loader.getController();
                controller.setData(selectedProduit, this);

                // Setup the stage
                Stage stage = new Stage();
                stage.setTitle("Modifier un Produit");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                System.err.println("Error loading ModifierProduit.fxml: " + e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void refreshData() {
        try {
            List<Produit> list = ps.select();
            obs.setAll(list);
        } catch (SQLException e) {
            System.err.println("Error refreshing data: " + e.getMessage());
        }
    }
    @FXML
    void search() {
        String searchText = searchField.getText().toLowerCase();
        List<Produit> filteredList = obs.stream()
                .filter(produit -> produit.getNom().toLowerCase().contains(searchText)
                        || produit.getDescription().toLowerCase().contains(searchText)
                        || produit.getSponsor().getNom().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        obs.setAll(filteredList);
    }

    @FXML
    void clearSearch(ActionEvent event) {
        searchField.clear();
        refreshData(); // Reset the table to show all products
    }


}
