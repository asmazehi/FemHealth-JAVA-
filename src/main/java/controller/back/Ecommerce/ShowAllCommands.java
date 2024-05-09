package controller.back.Ecommerce;

import controller.Sponsoring.AfficherProduitFrontController;
import controller.front.Ecommerce.DetailCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Ecommerce.Commande;
import service.Ecommerce.CommandeService;
import service.Ecommerce.PanierService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllCommands {

    @FXML
    private Button BackEcommerce;
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
    Button TerminerButton;

    @FXML
    Button DetailButton;

    @FXML
    private HBox idcard;
    @FXML
    private Button Dashboard;

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
        Button TerminerButton = new Button("Terminer");
        Button detailButton = new Button("Detail");

        hbox.getChildren().addAll(DateLable, AdressLabel, PaiementLable, LivraisonLabel, StatutLabel, TerminerButton, detailButton);
        hbox.setSpacing(70);
        hbox.setAlignment(Pos.CENTER_LEFT);

        TerminerButton.setOnAction(event -> {
            try {
                obj.setStatut("Terminé");
                commandeService.update(obj); // Mettre à jour la commande dans la base de données

                //this.padString(o)
                DateLable.setText("" + obj.getDateC() + "");
                AdressLabel.setText(obj.getAdresse() + "");
                PaiementLable.setText("" + obj.getMpaiement() + "");
                LivraisonLabel.setText("" + obj.getMlivraison());
                StatutLabel.setText("" + obj.getStatut());

                TerminerButton.setVisible(false); // Masquer le bouton "Annuler"

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
            TerminerButton.setVisible(false);
        }

        detailButton.setOnAction(event -> {
            Commande commande = obj;
            try {
                int idp = commande.getIdPanier();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Ecommerce/DetailCommand.fxml"));
                Parent root = loader.load();
                DetailCommandeBack controller = loader.getController();
                controller.initialize(idp);
                detailButton.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return hbox;
    }

    public static String padString(String input, int desiredLength) {
        if (input.length() >= desiredLength) {
            return input; // Si la longueur de la chaîne est déjà supérieure ou égale à desiredLength, retourne la chaîne d'origine
        } else {
            // Calcule le nombre d'espaces à ajouter
            int spacesToAdd = desiredLength - input.length();
            // Crée une chaîne contenant le nombre d'espaces requis
            String spaces = new String(new char[spacesToAdd]).replace("\0", " ");
            // Concatène la chaîne d'origine avec les espaces
            return input + spaces;
        }
    }
@FXML
    private void ouvrirPageWeb() {
        try {
            // URL de la page à ouvrir
            String url = "https://app.powerbi.com/view?r=eyJrIjoiNWM2NDViZjQtZWVlNS00ZGFlLTliZjYtM2FiNTQ0OWZhZTkzIiwidCI6ImRiZDY2NjRkLTRlYjktNDZlYi05OWQ4LTVjNDNiYTE1M2M2MSIsImMiOjl9";

            // Ouvrir la page dans le navigateur par défaut du système
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void BackEcommerce(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/BaseAdmin.fxml"));
            Parent root = loader.load();
            BackEcommerce.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Error loading : " + e.getMessage());
        }

    }

}
