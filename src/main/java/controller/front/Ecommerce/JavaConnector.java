package controller.front.Ecommerce;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import controller.front.Ecommerce.StripePaiement;

import java.io.IOException;

public class JavaConnector {

    private final WebEngine webEngine;

    private final WebView webView;
    private  int idpanier;

    public JavaConnector(WebEngine webEngine, WebView webView, int idpanier) {
        this.webEngine = webEngine;
        this.webView = webView;
        this.idpanier=idpanier;
        initializeListener();
        System.out.println("java connector constructeur appeler");
    }
    private void initializeListener() {
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("javaConnector", this);
        System.out.println("initializeListener() applé");
    }

    public void continueShopping() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherListCommandeparClient.fxml"));
                Parent root = loader.load();
                AfficherListCommandeparClient controller = loader.getController();

                Scene currentScene = webView.getScene();



                // Remplacement du contenu de la scène par celui du fichier FXML
                currentScene.setRoot(root);

                System.out.println("continueShopping() appelé");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }


    public void cancelpaiement() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherListCommandeparClient.fxml"));
                Parent root = loader.load();
                Scene currentScene = webView.getScene();

                currentScene.setRoot(root);

                System.out.println("cancelpaiement() appelé");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }

}
