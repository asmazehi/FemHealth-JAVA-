package test;

<<<<<<< HEAD
=======

>>>>>>> origin/main
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
=======
import javafx.stage.Stage;

>>>>>>> origin/main
import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Blog/carCard.fxml"));
            Parent root = loader.load();
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gérer les publication");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }
=======
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back/Sponsoring/AfficherSponsor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gérer les Sponsors");
            primaryStage.show();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

>>>>>>> origin/main
}
