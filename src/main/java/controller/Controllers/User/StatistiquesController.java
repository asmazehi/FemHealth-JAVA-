package controller.Controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User.Utilisateur;
import service.User.UtilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StatistiquesController {

    @FXML
    public BarChart<String, Integer> stat1FX;
    @FXML
    public PieChart stat2FX;
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

//        Map<Date, Integer> userCountByDate = new HashMap<>();
//
//        try {
//            List<Utilisateur> utilisateurs = utilisateurService.select();
//            // Parcourir la liste des utilisateurs pour compter le nombre d'utilisateurs par date
//            for (Utilisateur utilisateur : utilisateurs) {
//                Date registrationDate = utilisateur.getRegistred_at();
//                // Vérifier si la date existe déjà dans la map
//                if (userCountByDate.containsKey(registrationDate)) {
//                    // Si oui, incrémenter le compteur
//                    userCountByDate.put(registrationDate, userCountByDate.get(registrationDate) + 1);
//                } else {
//                    // Sinon, ajouter la date à la map avec un compteur initialisé à 1
//                    userCountByDate.put(registrationDate, 1);
//                }
//            }

            // Mettre à jour le graphique stat1FX avec les données
            // Vous devez déterminer comment organiser les données pour que le graphique les affiche correctement
            // Par exemple, vous pourriez utiliser une itération sur les entrées de la map pour ajouter les données au graphique
            // ...
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private void updateStat2FX() {
        int activeCount = 0;
        int inactiveCount = 0;

        try {
            List<Utilisateur> utilisateurs = utilisateurService.select();
            // Parcourir la liste des utilisateurs pour compter le nombre de comptes actifs et désactivés
            for (Utilisateur utilisateur : utilisateurs) {
                if (utilisateur.getActive() == 1) {
                    activeCount++;
                } else {
                    inactiveCount++;
                }
            }

            // Mettre à jour le graphique stat2FX avec les données
            // Vous devez ajouter les données de comptage au graphique, par exemple, en utilisant des objets PieChart.Data
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode appelée lors du clic sur le graphique des utilisateurs inscrits par jour
    @FXML
    public void ConnexionParJours(MouseEvent mouseEvent) {
        updateStat1FX();
    }

    // Méthode appelée lors du clic sur le graphique des comptes actifs/désactivés
    @FXML
    public void SituationsComptes(MouseEvent mouseEvent) {
        updateStat2FX();
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
