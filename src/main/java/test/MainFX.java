package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

/*public class MainFX extends Application {
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
    }}*/




public class MainFX extends Application {

    public static void main(String[] args) {
        //System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherProduit.fxml"));

            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Ecommerce/ShowAllCommands.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Sponsoring/AfficherProduitF.fxml"));
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
