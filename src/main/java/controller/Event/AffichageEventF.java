package controller.Event;

import controller.Sponsoring.AfficherProduitFrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.User.Utilisateur;
import model.events.Evenement;
import service.events.EvenementC;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffichageEventF {

    @FXML
    private ChoiceBox<String> ECommerce;

    @FXML
    private FlowPane eventFlowPane;
    @FXML
    private ChoiceBox<String> choiceBoxEvents;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<String> choiceBoxTypes;
    private EvenementC evenementService = new EvenementC();
    @FXML
    private ChoiceBox<String> choiceBoxMonCompte;
    private Utilisateur CurrentUser;

    public void SetData(Utilisateur user){
        this.CurrentUser = user;
    }

    @FXML
    public void initialize() {
        try {
            List<Evenement> evenementList = evenementService.select();

            for (Evenement evenement : evenementList) {
                AnchorPane card = createEvenementCard(evenement);
                eventFlowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }

        loadEvents();
        // Add listener to searchField text property
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Perform search whenever the text changes
            loadEvents();
        });
        loadEventTypes();

        choiceBoxMonCompte.setItems(FXCollections.observableArrayList("Se déconnecter", "Gérer le profil"));


        ECommerce.setItems(FXCollections.observableArrayList("Produits Disponibles", "Voir Panier", "Voir Commandes"));
        ECommerce.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Produits Disponibles":
                    navigateToProductPage();
                    break;
                case "Voir Panier":
                    navigateToPanierPage();
                    break;
                case "Voir Commandes":
                    navigateToCommandesPage();
                    break;
                default:
                    break;
            }
        });



    }
    private void loadEventTypes() {
        try {
            List<String> types = evenementService.getAllTypes();
            ObservableList<String> typeOptions = FXCollections.observableArrayList(types);
            choiceBoxTypes.setItems(typeOptions);

            // Ajouter un gestionnaire d'événements pour détecter les changements de sélection
            choiceBoxMonCompte.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Charger les événements du type sélectionné
                    try {
                        loadEventsByType(newValue);
                    } catch (SQLException e) {
                        System.err.println("Error loading events by type: " + e.getMessage());
                    }
                    switch (newValue) {
                        case "Se déconnecter":
                            navigateToHomePage();
                            break;
                        case "Gérer le profil":
                            navigateToEditProfilPage();
                            break;
                        default:
                            break;
                    }
                }
            });


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void navigateToCommandesPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/AfficherListCommandeparClient.fxml"));
        try {
            AnchorPane commandesPage = loader.load();
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the home page
            root.getChildren().setAll(commandesPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Parent root = loader.load();
        //AnchorPane pageProduct = loader.load();
        Scene scene = eventFlowPane.getScene();

    }
    private void navigateToPanierPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Ecommerce/ShowPanier1.fxml"));
        try {
            AnchorPane panierPage = loader.load();
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the home page
            root.getChildren().setAll(panierPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Parent root = loader.load();
        //AnchorPane pageProduct = loader.load();
        Scene scene = eventFlowPane.getScene();

    }
    private void navigateToProductPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/Sponsoring/AfficherProduitF.fxml"));
        try {
            AnchorPane produitPage = loader.load();
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the home page
            root.getChildren().setAll(produitPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Parent root = loader.load();
        //AnchorPane pageProduct = loader.load();
        Scene scene = eventFlowPane.getScene();

        //AnchorPane root = (AnchorPane) scene.getRoot();
    }

    private void loadEventsByType(String type) throws SQLException {
        eventFlowPane.getChildren().clear(); // Effacer les événements précédents

        // Charger les événements du type spécifié
        List<Evenement> evenementList = evenementService.getEventsByType(type);

        // Ajouter les nouveaux événements à l'interface utilisateur
        for (Evenement evenement : evenementList) {
            eventFlowPane.getChildren().add(createEvenementCard(evenement));
        }
    }

    private void loadEvents() {
        try {
            eventFlowPane.getChildren().clear();

            List<Evenement> evenementList;
            if (searchField.getText().isEmpty()) {
                evenementList = evenementService.select();
            } else {
                evenementList = evenementService.searchByName(searchField.getText());
            }

            for (Evenement evenement : evenementList) {
                AnchorPane card = createEvenementCard(evenement);
                eventFlowPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }
    }

    @FXML
    private void searchEvents() {
        loadEvents();
    }

    private AnchorPane createEvenementCard(Evenement evenement) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(180, 300); // Fixed size for each card

        // Add border to the card
        card.setStyle("-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 5px;");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(15);
        imageView.setLayoutY(15);
        imageView.getStyleClass().add("evenement-image");

        File file = new File(evenement.getImage());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

        Label nomLabel = new Label(evenement.getNom());
        nomLabel.setLayoutX(15);
        nomLabel.setLayoutY(180);
        nomLabel.getStyleClass().add("evenement-nom");

        Label dateDebutLabel = new Label("Date début: " + evenement.getDateDebut());
        dateDebutLabel.setLayoutX(15);
        dateDebutLabel.setLayoutY(200);
        dateDebutLabel.getStyleClass().add("evenement-date-debut");

        Label dateFinLabel = new Label("Date fin: " + evenement.getDateFin());
        dateFinLabel.setLayoutX(15);
        dateFinLabel.setLayoutY(220);
        dateFinLabel.getStyleClass().add("evenement-date-fin");

        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        localisationLabel.setLayoutX(15);
        localisationLabel.setLayoutY(240);
        localisationLabel.getStyleClass().add("evenement-localisation");

        Label montantLabel = new Label("Montant: " + evenement.getMontant());
        montantLabel.setLayoutX(15);
        montantLabel.setLayoutY(260);
        montantLabel.getStyleClass().add("evenement-montant");


        Button reserverButton = new Button("Réserver");
        reserverButton.setLayoutX(15);
        reserverButton.setLayoutY(280);
        reserverButton.getStyleClass().add("evenement-reserver-button");
        reserverButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/ReserverF.fxml"));
                AnchorPane reservationPage = loader.load();

                // Pass the event ID to the AjouterResB controller
                AjouterResB controller = loader.getController();
                controller.initData(evenement.getId()); // Pass the event ID here

                // Access the scene and root node of the current anchor pane
                Scene scene = card.getScene();
                AnchorPane root = (AnchorPane) scene.getRoot();

                // Replace the content of the root with the reservation page
                root.getChildren().setAll(reservationPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Voir Événements", "Voir Réservations");
        choiceBox.setLayoutX(15);
        choiceBox.setLayoutY(280);
        choiceBox.setOnAction(this::handleChoiceBoxAction);

        card.getChildren().addAll(imageView, nomLabel, dateDebutLabel, dateFinLabel, localisationLabel, montantLabel, reserverButton);
        return card;
    }

    // Define method to handle ChoiceBox action
    @FXML
    private void handleChoiceBoxAction(ActionEvent event) {
        String selectedOption = choiceBoxEvents.getValue();
        switch (selectedOption) {
            case "Voir Événements":
                navigateToEventsPage();
                break;
            case "Voir Réservations":
                navigateToReservationsPage();
                break;
            default:
                break;
        }
    }

    private void navigateToEventsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageEventF.fxml"));
            AnchorPane eventsPage = loader.load();

            // Access the scene and root node of the current anchor pane
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the events page
            root.getChildren().setAll(eventsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToReservationsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.Event/AffichageResF.fxml"));
            AnchorPane reservationsPage = loader.load();

            // Access the scene and root node of the current anchor pane
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the reservations page
            root.getChildren().setAll(reservationsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void navigateToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/HomePage.fxml"));
            AnchorPane homePage = loader.load();

            // Access the scene and root node of the current anchor pane
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the home page
            root.getChildren().setAll(homePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToEditProfilPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/EditProfil.fxml"));
            AnchorPane editProfilPage = loader.load();

            // Access the scene and root node of the current anchor pane
            Scene scene = eventFlowPane.getScene();
            AnchorPane root = (AnchorPane) scene.getRoot();

            // Replace the content of the root with the edit profile page
            root.getChildren().setAll(editProfilPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Other methods for handling navigation to different pages
}