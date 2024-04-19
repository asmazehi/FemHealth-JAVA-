package controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;
import controller.Event.ModifierEventB;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEventB {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Evenement, Date> date_debutCol;

    @FXML
    private TableColumn<Evenement, Date> date_finCol;

    @FXML
    private TableColumn<Evenement, String> imageCol;

    @FXML
    private TableColumn<Evenement, String> localisationCol;

    @FXML
    private TableColumn<Evenement, Integer> montantCol;

    @FXML
    private TableColumn<Evenement, String> nomCol;

    @FXML
    private TableView<Evenement> tableview;

    @FXML
    private TableColumn<Evenement, Type> type_idCol;

    @FXML
    private Label welcomelb;


    private EvenementC ec = new EvenementC();
    private ObservableList<Evenement> obs;

   /* public class ImageViewTableCellFactory implements Callback<TableColumn<Evenement, String>, TableCell<Evenement, String>> {

        @Override
        public TableCell<Evenement, String> call(TableColumn<Evenement, String> param) {
            return new TableCell<Evenement, String>() {
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (imagePath == null || empty) {
                        setGraphic(null);
                    } else {
                        Image image = new Image("file:" + imagePath);
                        imageView.setImage(image);
                        imageView.setFitWidth(50); // Adjust as needed
                        imageView.setFitHeight(50); // Adjust as needed
                        setGraphic(imageView);
                    }
                }
            };
        }
*/
        @FXML
        void supprimerEventB(ActionEvent event) {
            try {
                Evenement e = tableview.getSelectionModel().getSelectedItem();
                ec.delete(e.getId());
                obs.remove(e);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }


        @FXML
        void initialize() {
            try {
                List<Evenement> list = ec.select();
                obs = FXCollections.observableArrayList(list);
                tableview.setItems(obs);
                type_idCol.setCellValueFactory(new PropertyValueFactory<>("type_id"));
                nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
                imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
                localisationCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));
                montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));


                // Personnalisation de l'affichage des dates
                date_debutCol.setCellFactory(new Callback<TableColumn<Evenement, Date>, TableCell<Evenement, Date>>() {
                    @Override
                    public TableCell<Evenement, Date> call(TableColumn<Evenement, Date> column) {
                        return new TableCell<Evenement, Date>() {
                            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            @Override
                            protected void updateItem(Date date, boolean empty) {
                                super.updateItem(date, empty);
                                if (date == null || empty) {
                                    setText(null);
                                } else {
                                    setText(dateFormat.format(date));
                                }
                            }
                        };
                    }
                });

                date_finCol.setCellFactory(new Callback<TableColumn<Evenement, Date>, TableCell<Evenement, Date>>() {
                    @Override
                    public TableCell<Evenement, Date> call(TableColumn<Evenement, Date> column) {
                        return new TableCell<Evenement, Date>() {
                            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            @Override
                            protected void updateItem(Date date, boolean empty) {
                                super.updateItem(date, empty);
                                if (date == null || empty) {
                                    setText(null);
                                } else {
                                    setText(dateFormat.format(date));
                                }
                            }
                        };
                    }
                });

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }


        @FXML
        void modifierEventB(ActionEvent event) {
            // Récupérer l'événement sélectionné dans le TableView
            Evenement evenement = tableview.getSelectionModel().getSelectedItem();

            if (evenement != null) {
                try {
                    // Charger la vue de modification avec le contrôleur correspondant
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Back.Event/ModifierEventB.fxml"));
                    Parent root = loader.load();

                    // Passer l'événement à modifier au contrôleur de modification
                    ModifierEventB controller = loader.getController();
                    controller.initData(evenement);

                    // Créer une nouvelle scène
                    Scene scene = new Scene(root);

                    // Obtenir la scène actuelle et changer sa racine
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur de chargement de la vue de modification
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to load ModifierEventB.fxml");
                    alert.show();
                }
            } else {
                // Afficher un message si aucun événement n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setContentText("Veuillez sélectionner un événement à modifier");
                alert.show();
            }
        }
    }





