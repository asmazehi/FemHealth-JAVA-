package test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AfficherEventB.fxml"));
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AjouterEventB.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/AjouterResB.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gérer les events");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

}}