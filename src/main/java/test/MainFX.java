package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherProduit.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Sponsoring/AfficherProduitF.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Blog/AfficherCommentaire.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/HomePage" + ".fxml"));
            Parent root = loader.load();
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("FemHealth");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }
}
