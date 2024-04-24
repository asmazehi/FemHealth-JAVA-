package test;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainFX extends Application {
    public static void main(String[] args) {launch(args);}
    @Override
    public  void start(Stage primaryStage){
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Blog/updatePublication.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/ShowPanier1.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gérer Commande");
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }}

