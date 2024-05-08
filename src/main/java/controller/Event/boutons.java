package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class boutons {

    @FXML
    private void goToPage1(ActionEvent event) {
        loadPage("/Back.Event/AfficherEventB.fxml");
    }

    @FXML
    private void goToPage2(ActionEvent event) {
        loadPage("/Back.Event/AfficherResB.fxml");
    }

    @FXML
    private void goToPage3(ActionEvent event) {
        loadPage("/Back.Event/AfficherTypeB.fxml");
    }

    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

