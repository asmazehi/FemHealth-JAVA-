package controller.Sponsoring;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import model.Sponsoring.Sponsor;
import service.Sponsoring.SponsorService;

import java.sql.SQLException;
import java.util.List;

public class StatController {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label titleLabel;

    public void initialize() {
        populatePieChart();
    }

    private void populatePieChart() {
        SponsorService sponsorService = new SponsorService();
        try {
            List<Sponsor> sponsors = sponsorService.select();
            for (Sponsor sponsor : sponsors) {
                pieChart.getData().add(new PieChart.Data(sponsor.getNom(), sponsor.getAmountOfProducts()));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sponsor data: " + e.getMessage());
        }
    }

    public void setData() {
        titleLabel.setText("Statistics of Sponsor Products");
    }

}
