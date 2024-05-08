package controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import model.events.Evenement;
import service.events.EvenementC;

import java.sql.SQLException;
import java.util.List;

public class StatController {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label titleLabel;

    public void initialize() {
        try {
            EvenementC evenementC = new EvenementC();
            List<Evenement> evenements = evenementC.selectWithReservationCount();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Evenement evenement : evenements) {
                PieChart.Data data = new PieChart.Data(evenement.getNom(), evenement.getReservationCount());
                pieChartData.add(data);
                data.setName(data.getName() + " (" + (int) data.getPieValue() + ")");
            }

            pieChart.setData(pieChartData);
            pieChart.setTitle("Reservation Statistics");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

}
