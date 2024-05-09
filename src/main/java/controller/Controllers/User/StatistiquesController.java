package controller.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User.Utilisateur;
import service.User.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatistiquesController {

    @FXML
    public BarChart<String, Integer> stat1FX;
    @FXML
    public PieChart stat2FX;
    @FXML
    private Label activeLabel;

    @FXML
    private Label inactiveLabel;
    public Button RetourFX;

    UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void initialize() {
        // Mettre à jour les graphiques lors du chargement de la vue
        updateCharts();
    }

    public void updateCharts() {

        updateStat1FX();


        updateStat2FX();
    }
    private void updateStat1FX() {
        // Réinitialiser les données du graphique
        stat1FX.getData().clear();

        try {
            // Récupérer les utilisateurs depuis la base de données
            List<Utilisateur> utilisateurs = utilisateurService.select();

            // Initialiser une série de données pour le graphique
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Initialiser une structure de données pour stocker le compte d'utilisateurs par jour
            Map<String, Integer> userCountByDate = new HashMap<>();

            // Parcourir les utilisateurs pour calculer le compte par jour
            for (Utilisateur utilisateur : utilisateurs) {
                java.sql.Date registrationDate = utilisateur.getRegistered_at();
                if (registrationDate != null) {
                    String dateString = registrationDate.toString();
                    userCountByDate.put(dateString, userCountByDate.getOrDefault(dateString, 0) + 1);
                }
            }

            // Ajouter les données à la série
            for (Map.Entry<String, Integer> entry : userCountByDate.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            // Ajouter la série au graphique
            stat1FX.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void updateStat2FX() {
        // Réinitialiser les données du graphique
        stat2FX.getData().clear();

        try {
            // Récupérer les utilisateurs depuis la base de données
            List<Utilisateur> utilisateurs = utilisateurService.select();

            // Compter les utilisateurs actifs et inactifs
            int activeCount = 0;
            int inactiveCount = 0;
            for (Utilisateur utilisateur : utilisateurs) {
                if (utilisateur.getActive() == 1) {
                    activeCount++;
                } else {
                    inactiveCount++;
                }
            }

            // Ajouter les données au graphique
            stat2FX.getData().add(new PieChart.Data("Actifs", activeCount));
            stat2FX.getData().add(new PieChart.Data("Inactifs", inactiveCount));
            activeLabel.setText("Actifs : " + activeCount);
            inactiveLabel.setText("Inactifs : " + inactiveCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void retourToGererUtilisateurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/GererUtilisateur.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) RetourFX.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}