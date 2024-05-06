package controller.front.Ecommerce;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CardPanierController {



    @FXML
    private Label DeletID;

    @FXML
    private Label NomProduitID;

    @FXML
    private Label PrixID;

    @FXML
    private Label QuantiteID;

    @FXML
    private Label TotalProduit;

        // Méthodes pour définir les informations de la carte
        public void setNomProduit(String nomProduit) {
            NomProduitID.setText(nomProduit);
        }

        public void setQuantite(String quantite) {
            QuantiteID.setText(quantite);
        }

        public void setPrix(String prix) {
            PrixID.setText(prix);
        }

        public void setTotal(String total) {
            TotalProduit.setText(total);
        }
    }


