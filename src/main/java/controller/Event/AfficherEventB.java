package controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.events.Evenement;
import model.events.Type;
import service.events.EvenementC;

import java.net.URL;
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
}