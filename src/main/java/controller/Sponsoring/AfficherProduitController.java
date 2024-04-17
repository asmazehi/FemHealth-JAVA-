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
            imagePathP.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

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
                } catch (SQLException e) {
                    System.err.println("Error deleting product: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void naviguerVersAjouterProduit(ActionEvent event) {
        if (!ajouterProduitPageOpen) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AjouterProduit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Ajouter un Produit");
                stage.setOnCloseRequest(e -> {
                    ajouterProduitPageOpen = false;
                });
                stage.show();

                ajouterProduitPageOpen = true;

                Stage currentStage = (Stage) tableView.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                System.err.println("Error loading AjouterProduit.fxml: " + e.getMessage());
            }
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        Produit selectedProduit = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/ModifierProduit.fxml"));
                Parent root = loader.load();
                ModifierProduitController controller = loader.getController(); // Correct controller type
                controller.setData(selectedProduit, this);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modifier un Produit");
                stage.show();

            } catch (IOException e) {
                System.err.println("Error loading ModifierProduit.fxml: " + e.getMessage());
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

}
