package controller.front.Ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.Ecommerce.Commande;
import service.Ecommerce.CommandeService;

import java.io.IOException;
import java.sql.SQLException;

public class PasserCommandeContoller {
    private  int idp;

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
    private Label livraisoncontrol;

    @FXML
    private Label paiementcontrol;

    @FXML
    private Label phonecontrol;
    @FXML
    private Label AdressControl;
public void setIdp(int idp){
    this.idp=idp;
    System.out.println("ID du panier dans setIdp : " + idp);

}
    @FXML
    void AddCommande(ActionEvent event) {
        String phoneN = phone.getText();
        String AdressN = Adress.getValue();
        String MlivraisonN = Mlivraison.getValue();
        String MpaiementN = Mpaiement.getValue();
        Commande commande = new Commande(idp, AdressN, MpaiementN, phoneN, MlivraisonN);
        CommandeService commandeService = new CommandeService();
        boolean isValid = true;

            // Validation de l'adresse
            if (Adress.getValue() == null || Adress.getValue().isEmpty()) {
                AdressControl.setText("Veuillez sélectionner une adresse.");
                isValid = false;
            } else {
                AdressControl.setText("");
            }

            // Validation du mode de paiement
            if (Mpaiement.getValue() == null || Mpaiement.getValue().isEmpty()) {
                paiementcontrol.setText("Veuillez sélectionner un mode de paiement.");
                isValid = false;
            } else {
                paiementcontrol.setText("");
            }

            // Validation du mode de livraison
            if (Mlivraison.getValue() == null || Mlivraison.getValue().isEmpty()) {
                livraisoncontrol.setText("Veuillez sélectionner un mode de livraison.");
                isValid = false;
            } else {
                livraisoncontrol.setText("");
            }

        if (phone.getText() == null || phone.getText().isEmpty()) {
            phonecontrol.setText("Veuillez saisir un numéro de téléphone.");
            isValid = false;
        } else if (!phone.getText().matches("^19[0-9]{6,7}$")) {
            phonecontrol.setText("Veuillez saisir un numéro de téléphone valide");
            isValid = false;
        } else {
            phonecontrol.setText("");
        }

            // Si toutes les validations passent, ajoutez la commande
            if (isValid) {
                AddCommande(event);
            }
        try {
            System.out.println("ID du panier avant de charger la nouvelle vue : " + idp);

            commandeService.add(commande);

            // Charger la vue AfficherCommande.fxml après avoir ajouté la commande
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherCommande.fxml"));
            Parent root = loader.load();
            AfficherCommande controller = loader.getController();
            controller.initialize(idp);
            System.out.println(idp+"///// hethii ba3eddd");
            addC.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
}}





    @FXML
    void afficherpanier(ActionEvent event) {
     //hethii traja3 el page panier

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/ShowPanier1.fxml"));
            Parent root = loader.load();
            ShowPanier controller = loader.getController();
            updateP.getScene().setRoot(root);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
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
