package controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterEventB {

    @FXML
    private TextField dateDebutTF;

    @FXML
    private TextField dateFinTF;

    @FXML
    private TextField imageTF;

    @FXML
    private TextField localisationTF;

    @FXML
    private TextField montantTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField type_idTF;

    @FXML
    void AfficherEventB(ActionEvent event) {

    }


    @FXML
    void AjouterEventB(ActionEvent event) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Parse the type_id from TextField
        int type_idValue = Integer.parseInt(type_idTF.getText());

        // Fetch the Type object based on the type_idValue
        Type type_id = null;
        try {
            type_id = EvenementC.getType_id(type_idValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Check if the Type object exists
        if (type_id != null) {
            try {
                String nom = nomTF.getText();
                Date dateDebut = sdf.parse(dateDebutTF.getText());
                Date dateFin = sdf.parse(dateFinTF.getText());
                String image = imageTF.getText();
                String localisation = localisationTF.getText();
                float montant = Float.parseFloat(montantTF.getText());

                // Create Evenement object with fetched Type
                Evenement ev = new Evenement(type_id, nom, dateDebut, dateFin, image, localisation, montant);

                // Call the add method from EvenementC to add the event
                EvenementC ec = new EvenementC();


                ec.add(ev);

                // Show success alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Event added successfully");
                alert.show();
            } catch (ParseException | SQLException e) {
                // Handle parsing or database error
                e.printStackTrace();
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to add event");
                alert.show();
            }
        }
    }





}


