package controller.front.Ecommerce;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
        try {
            System.out.println("ID du panier avant de charger la nouvelle vue : " + idp);

            // Ajouter la commande avant de charger la nouvelle vue
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
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
