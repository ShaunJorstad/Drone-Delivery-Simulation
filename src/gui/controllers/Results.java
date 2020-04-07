/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the Results FXML document
 */

package gui.controllers;

import cli.SimulationThread;
import cli.SimController;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Results implements Initializable {
    public GridPane resultsGrid;
    SimulationThread statusThread;

    public Button exportButton;
    public Button results;
    public Button home;
    public Button settings;
    public VBox vBox;
    public Label resultsTitle;
    public HBox navBar;
    public Button back;
    public ImageView backImage;

    boolean initial = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        File backFile;
        if (Navigation.isEmpty()) {
            backFile = new File("assets/icons/backGray.png");
        } else {
            backFile = new File("assets/icons/backBlack.png");
        }
        Image backArrowImage = new Image(backFile.toURI().toString());
        backImage.setImage(backArrowImage);
        backImage.setFitHeight(16);
        backImage.setFitWidth(16);
        backImage.setPreserveRatio(true);

        SimController simController = SimController.getInstance();
        exportButton.setDisable(true);
        if (simController.hasResults()) {
            displayResults();
            exportButton.setDisable(false);
        }

        injectCursorStates();
    }

    public void injectCursorStates() {
        List<Button> items = Arrays.asList(home, settings, results, back, exportButton);
        for (Button item : items) {
            item.setOnMouseEntered(mouseEvent -> {
                if (initial) {
                    initial = false;
                } else {
                    item.getScene().setCursor(Cursor.HAND);
                }
            });
            item.setOnMouseExited(mouseEvent -> {
                item.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Results");
    }

    public void handleExport(ActionEvent actionEvent) {
        SimController.exportResults((Stage) home.getScene().getWindow());
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Results");
    }

    public void handleNavigateResults(ActionEvent actionEvent) {
        System.out.println("already in settings");
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }

    public void displayResults() {
        ArrayList<simulation.Results> fifoData = SimController.getAggregatedResultsFIFO();
        ArrayList<simulation.Results> knapsackData = SimController.getAggregatedResultsKnapsack();


        double fifoWorst = SimController.getAggregatedWorstTime(fifoData);
        double fifoAverage = SimController.getAggregatedAvgTime(fifoData);
        double knapsackWorst = SimController.getAggregatedWorstTime(knapsackData);
        double knapsackAverage = SimController.getAggregatedAvgTime(knapsackData);

        Text fWorst = new Text(Double.toString(fifoWorst));
        Text fAverage = new Text(Double.toString(fifoAverage));
        Text kWorst = new Text(Double.toString(knapsackWorst));
        Text kAverage = new Text(Double.toString(knapsackAverage));

        fWorst.getStyleClass().add("resultsTableText");
        fAverage.getStyleClass().add("resultsTableText");
        kAverage.getStyleClass().add("resultsTableText");
        kWorst.getStyleClass().add("resultsTableText");

        resultsGrid.add(fWorst, 2, 1);
        resultsGrid.add(fAverage, 1, 1);
        resultsGrid.add(kWorst, 2, 2);
        resultsGrid.add(kAverage, 1, 2);

        exportButton.setDisable(false);

        // inserts line graph
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Simulation #");
        yAxis.setLabel("Average runtime");
        LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setTitle("Average runtime comparison");
        XYChart.Series<Number, Number> fifo = new XYChart.Series<>();
        fifo.setName("Fifo Average");
        XYChart.Series<Number, Number> knapsack = new XYChart.Series<>();
        knapsack.setName("Knapsack Average");

        //populate lines with numbers
        int index = 0;
        for (simulation.Results point : fifoData) {
            fifo.getData().add(new XYChart.Data<>(index, point.getAvgTime()));
            index += 1;
        }
        index = 0;
        for (simulation.Results point : knapsackData) {
            knapsack.getData().add(new XYChart.Data<>(index, point.getAvgTime()));
            index += 1;
        }

        chart.getData().add(fifo);
        chart.getData().add(knapsack);

        vBox.getChildren().add(chart);
    }
}
