package controller.front.Ecommerce;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Ecommerce.PanierItem;
import netscape.javascript.JSObject;
import service.Ecommerce.PanierService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StripePaiement {
    private final PanierService panierService = new PanierService();

    @FXML
    private ResourceBundle resources;

    @FXML
    private WebView webid;
    int idpanier;

    @FXML
    public void initialize(int id) {
        idpanier=id;
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51Op589Hvqq7mfMH0fdKOSMMO2pf9QxdTW3Q6pBG13IVODxd9uudifpaL9KS2NgJEG5DyVC7nLFr3XYe5QRmiQa0C009i4gvPkC";

            // Récupérer les informations sur le panier
            List<PanierItem> lineItems = panierService.afficherinfopanier(id);

            // Créer les éléments de ligne de paiement (line items)
            List<SessionCreateParams.LineItem> sessionLineItems = new ArrayList<>();
            for (PanierItem panierItem : lineItems) {
                sessionLineItems.add(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(Long.valueOf(panierItem.getQuantite()))
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (panierItem.getTotalpanier() * 100)) // Le montant doit être en cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(panierItem.getNomProduit())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );
            }

            // Créer les paramètres pour la création de la session de paiement
            SessionCreateParams.Builder paramsBuilder = new SessionCreateParams.Builder()
                    .setSuccessUrl("http://localhost/success.html") // Redirect URL after successful payment
                    .setCancelUrl("http://localhost/cancel.html") // Redirect URL after canceled payment
                    .addAllLineItem(sessionLineItems) // Ajouter les éléments de ligne de paiement à la session
                    .setMode(SessionCreateParams.Mode.PAYMENT); // Payment mode

            // Créer la session de paiement
            Session session = Session.create(paramsBuilder.build());

            System.out.println("Session créée. URL de redirection : " + session.getUrl());
            webid.getEngine().load(String.valueOf(new URI(session.getUrl())));

            // Ajouter un écouteur pour détecter le chargement complet de la page
            webid.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    // Le chargement de la page est terminé, initialiser JavaConnector
                    WebEngine webEngine = webid.getEngine();
                    JavaConnector javaConnector = new JavaConnector(webEngine,webid,idpanier);
                }
            });
        } catch (StripeException e) {
            System.out.println("Erreur lors de la création de la session de paiement : " + e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
