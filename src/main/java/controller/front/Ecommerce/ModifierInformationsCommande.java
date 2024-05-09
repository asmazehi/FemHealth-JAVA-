/*package controller.front.Ecommerce;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Ecommerce.Commande;
import service.Ecommerce.CommandeService;

public class ModifierInformationsCommande {

    @FXML
    private TextField addressTextField;

    @FXML
    private ChoiceBox<String> deliveryMethodChoiceBox;

    @FXML
    private ChoiceBox<String> paymentMethodChoiceBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button saveButton;

    CommandeService commandeService=new CommandeService();
    @FXML
    private void initialize() {
        loadData();
    }

    private void loadData() {
        try {
            Commande commande = commandeService.selectById(68);

            // Charger les données dans les champs de texte et les choix
            addressTextField.setText(commande.getAdresse());
            deliveryMethodChoiceBox.setValue(commande.getMlivraison());
            paymentMethodChoiceBox.setValue(commande.getMpaiement());
            phoneTextField.setText(commande.getPhone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        try {
            // Récupérer les nouvelles valeurs des champs de texte et des choix
            String newAddress = addressTextField.getText();
            String newDeliveryMethod = deliveryMethodChoiceBox.getValue();
            String newPaymentMethod = paymentMethodChoiceBox.getValue();
            String newPhone = phoneTextField.getText();

            // Mettre à jour la commande dans la base de données
            commandeService.updateCommande(idCommande, newAddress, newDeliveryMethod, newPaymentMethod, newPhone);

            // Afficher un message de confirmation à l'utilisateur
            System.out.println("Les informations de la commande ont été mises à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}*/
