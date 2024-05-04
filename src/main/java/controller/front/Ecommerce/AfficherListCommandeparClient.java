package controller.front.Ecommerce;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ecommerce.*;
import service.Ecommerce.CommandeService;
import service.Ecommerce.PanierService;

public class AfficherListCommandeparClient {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label AdressLabel;

    @FXML
    private Label AnnulerLabel;

    @FXML
    private Label DateLable;

    @FXML
    private Label DetailLabel;

    @FXML
    private Label LivraisonLable;

    @FXML
    private Label PaiementLable;

    @FXML
    private Label StatutLabel;

    @FXML
    Button annulerButton;

    @FXML
    Button DetailButton;

    @FXML
    private HBox idcard;

    @FXML
    private VBox vBoxContainer;
  int idClient=1;
    PanierService panierService= new PanierService();
    CommandeService commandeService= new CommandeService();
    List<Commande> objectList;

    {
        try {
            objectList = commandeService.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() throws SQLException {
        idcard.setSpacing(1000);
        Insets margins = new Insets(0, 50, 0, -10);
        for (Commande obj : objectList) {
            HBox hbox = createHBoxForItem(obj,margins);
            vBoxContainer.getChildren().addAll(hbox,createSeparator());



        }}



    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setOpacity(0.4);
        return separator;
    }


    private HBox createHBoxForItem(Commande obj, Insets margins) {
        HBox hbox = new HBox();
        hbox.setPadding(margins);
        Label DateLable = new Label("" + obj.getDateC() + "");
        Label AdressLabel = new Label("" + obj.getAdresse() + "");
        Label PaiementLable = new Label("" + obj.getMpaiement() + "");
        Label LivraisonLabel = new Label("" + obj.getMlivraison());
        Label StatutLabel = new Label("" + obj.getStatut());
        Button annulerButton = new Button("annuler");
        Button detailButton = new Button("Detail");

        hbox.getChildren().addAll(DateLable, AdressLabel, PaiementLable, LivraisonLabel, StatutLabel, annulerButton, detailButton);
        hbox.setSpacing(75);
        hbox.setAlignment(Pos.CENTER_LEFT);

        annulerButton.setOnAction(event -> {
            try {
                obj.setStatut("Annulée"); // Modifier le statut de la commande à "Annulée"
                commandeService.update(obj); // Mettre à jour la commande dans la base de données

                // Mettre à jour les Labels existants avec les nouvelles valeurs de commande
                DateLable.setText("" + obj.getDateC() + "");
                AdressLabel.setText(obj.getAdresse() + "");
                PaiementLable.setText("" + obj.getMpaiement() + "");
                LivraisonLabel.setText("" + obj.getMlivraison());
                StatutLabel.setText("" + obj.getStatut());

                annulerButton.setVisible(false); // Masquer le bouton "Annuler"

            } catch (SQLException e) {
                // Afficher une alerte en cas d'échec de mise à jour
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update the order status!");
                alert.showAndWait();
            }
        });

        if (obj.getStatut().equals("Annulée")||obj.getStatut().equals("Terminé")) {
            // Si le statut est "Annulée", masquez le bouton "Annuler"
            annulerButton.setVisible(false);
        }

        detailButton.setOnAction(event -> {
            Commande commande = obj;
            try {
                int idp = commande.getIdPanier();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/DetailCommand.fxml"));
                Parent root = loader.load();
                DetailCommand controller = loader.getController();
                controller.initialize(idp);
                detailButton.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return hbox;
    }


}


