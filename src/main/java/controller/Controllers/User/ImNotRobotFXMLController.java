package controller.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImNotRobotFXMLController implements Initializable {
    public Button ValiderFX;
    public ImageView image1FX;
    public ImageView image2FX;
    public ImageView image3FX;
    public ImageView image4FX;
    public ImageView image5FX;
    public ImageView image6FX;
    public ImageView image7FX;
    public ImageView image8FX;
    public ImageView recaptchaFX;
    public ImageView image9FX;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;

    private List<ImageView> images = new ArrayList<>();
    private List<Integer> hh = new ArrayList<>();
    private boolean stillok = true;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path = "src/main/resources/User/";
        List<String> imageCafe = new ArrayList<>();
        imageCafe.add("cafe1.jpg");
        imageCafe.add("cafe2V.jpg");
        imageCafe.add("cafe3.jpg");
        imageCafe.add("cafe4.jpg");
        imageCafe.add("cafe5.jpg");
        imageCafe.add("cafe6V.jpg");
        imageCafe.add("cafe7V.jpg");
        imageCafe.add("cafe8.jpg");
        imageCafe.add("cafe9.jpg");

        Collections.shuffle(imageCafe);

        images.add(image1FX);
        images.add(image2FX);
        images.add(image3FX);
        images.add(image4FX);
        images.add(image5FX);
        images.add(image6FX);
        images.add(image7FX);
        images.add(image8FX);
        images.add(image9FX);

        for (int i = 0; i < imageCafe.size(); i++) {
            if (imageCafe.get(i).equals("cafe2V.jpg") || imageCafe.get(i).equals("cafe6V.jpg") || imageCafe.get(i).equals("cafe7V.jpg")) {
                hh.add(i + 1);
            }
        }

        for (int i = 0; i < 9; i++) {
            try {
                InputStream stream = new FileInputStream(path + imageCafe.get(i));
                images.get(i).setImage(new Image(stream));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImNotRobotFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void clickImage(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        int index = images.indexOf(imageView);
        System.out.println(index);

        if (index != -1) {
            if (!hh.contains(index + 1)) {
                stillok = false;
            }
            imageView.setImage(null);
        } else {
            System.out.println("Clicked ImageView not found in the list.");
        }
    }

    @FXML
    private void valide(ActionEvent event) {
        stillok = true;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getImage() == null) {
                int index = i + 1;
                if (!hh.contains(index)) {
                    stillok = false;
                    break;
                }
            }
        }
        if (stillok) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Validation réussie !");
            alert.showAndWait();
            redirectToAuthentification();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Validation échouée. Veuillez réessayer.");
            alert.showAndWait();
        }
        AuthentificationController.isRobotVerified = true;
        redirectToAuthentification();
    }

    public List<ImageView> getImages() {
        return images;
    }
    private void redirectToAuthentification() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Authentification.fxml"));
            Parent root = loader.load();
            Scene scene = image1.getScene();
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}