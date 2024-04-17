package controller.front.Ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Ecommerce.Commande;
import service.Ecommerce.CommandeService;

import java.sql.SQLException;

public class PasserCommandeContoller {

    @FXML
    private     ChoiceBox<String> Adress;;

    @FXML
    private ChoiceBox<String> Mlivraison;

    @FXML
    private ChoiceBox<String> Mpaiement;

    @FXML
    private Button addC;

    @FXML
    private TextField phone;

    @FXML
    private Button updateP;

    @FXML
    void AddCommande(ActionEvent event) {
        String phoneN=phone.getText();
        String AdressN=Adress.getValue();
        String MlivraisonN=Mlivraison.getValue();
        String MpaiementN=Mpaiement.getValue();
        Commande commande=new Commande(6,AdressN,MpaiementN,phoneN,MlivraisonN);
        CommandeService commandeService=new CommandeService();
        try {
            commandeService.add(commande);
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ok bb");
            alert.setContentText("commande ajouter");
            alert.show();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();

        }

    }



    @FXML
    void afficherpanier(ActionEvent event) {

    }
    public void initialize() {
        // Ajouter des choix à la ListView Adress
        Adress.getItems().addAll("Ariana","Béja","Ben Arous","Bizerte", "Gabès", "Gafsa", "Jendouba","Kairouan","Kasserine","Kébili","Le Kef","Mahdia","La Manouba","Médenine");

        // Ajouter des choix à la ListView Mlivraison
        Mlivraison.getItems().addAll("Livraison standard", "Points relais", "Livraison sur rendez-vous");

        // Ajouter des choix à la ListView Mpaiement
        Mpaiement.getItems().addAll("Paiement par carte", "Paiement à la livraison", "Chèque");
    }
}
