package controller.back.Ecommerce;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView webid;

    @FXML
    void initialize() {
        // Créer un moteur WebEngine pour charger du contenu dans la WebView
        WebEngine webEngine = webid.getEngine();

        String url = "http://app.powerbi.com/view?r=eyJrIjoiMmMzMjJhN2MtYjYxMS00YjYyLWE2M2MtZmE5ODUyOTEyMmFlIiwidCI6ImRiZDY2NjRkLTRlYjktNDZlYi05OWQ4LTVjNDNiYTE1M2M2MSIsImMiOjl9";
        webEngine.load(url);

        // Si vous voulez charger du contenu HTML à la place
        // String htmlContent = "<html><body><h1>Hello, World!</h1></body></html>";
        // webEngine.loadContent(htmlContent);
    }
}
