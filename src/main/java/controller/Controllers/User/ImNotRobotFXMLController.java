package controller.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImNotRobotFXMLController implements Initializable {
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
        String path = "src/main/resources/Image";
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

        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        images.add(image6);
        images.add(image7);
        images.add(image8);
        images.add(image9);

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
        String id = imageView.getId();

        int index = Integer.parseInt(id.substring(id.length() - 1)) - 1;

        if (!hh.contains(index + 1)) {
            stillok = false;
        }

        imageView.setImage(null);
    }

    @FXML
    private void valide(ActionEvent event) {
        if (stillok) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Validation réussie !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setHeaderText(null);
            alert.setContentText("Validation échouée. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
    public List<ImageView> getImages() {
        return images;
    }
}
