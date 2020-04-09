/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the OrderDistribution FXML document
 */

package gui.controllers;

import cli.SimulationThread;
import cli.SimController;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDistribution implements Initializable {
    SimulationThread statusThread;

    public VBox navBarContainer;
    public HBox navBar;
    public Button home;
    public Button settings;
    public Button results;
    public HBox settingsNavBar;
    public Button foodItems;
    public Button mealItems;
    public Button orderDistribution;
    public Button map;
    public Button drone;
    public Button back;
    public ImageView backImage;
    public Button runSimButton;
    public Button exportSettingsButton;
    public Button importSettingsButton;
    public VBox settingButtons;
    public Text ordersTitle;
    public Text hoursTitle;
    public GridPane contentGrid;
    public ScrollPane scrollpane;
    public ImageView uploadImage;
    public ImageView downloadImage;

    private int gridIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        orderDistribution.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        GridPane.setMargin(ordersTitle, new Insets(0, 0, 0, 40));
        gridIndex = 1;

        SimController.setCurrentButton(runSimButton);

        loadIcons();
        injectCursorStates();
        inflateOrderDistribution();
        checkSimulationStatus();
    }

    public void loadIcons() {
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

        uploadImage.setImage(new Image(new File("assets/icons/upload.png").toURI().toString()));
        downloadImage.setImage(new Image(new File("assets/icons/download.png").toURI().toString()));
    }

    public void checkSimulationStatus() {
//        int status = SimController.getSimStatus();
//        if (status == -1 ) { // no simulation has been run
//            String settingsValidity = Settings.verifySettings();
//            if (!settingsValidity.equals("")) {
//                updateRunBtn(settingsValidity, false);
//            }
//        } else if (status >= 0 && status < 50) { // simulation in progress
//            runSimButton.setStyle("-fx-background-color: #1F232F");
//            runSimButton.setDisable(true);
//            statusThread.run();
//        }
//        else if (status == 50) {
//            runSimButton.setText("Run another Sim");
//        }
    }

    public void injectCursorStates() {
        List<Button> items = Arrays.asList(home, settings, results, back, runSimButton, foodItems, mealItems, orderDistribution, map, drone, importSettingsButton, exportSettingsButton);
        for (Button item : items) {
            item.setOnMouseEntered(mouseEvent -> {
                item.getScene().setCursor(Cursor.HAND);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root, "MealItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "Map", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow());
        Navigation.pushScene("OrderDistribution");
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }

    public void handleRunSimulation(ActionEvent actionEvent) throws IOException {
        if (SimController.simRan) {
            Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
            Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
            Navigation.pushScene("Drone");
            return;
        }
        runSimButton.setStyle("-fx-background-color: #1F232F");
        runSimButton.setText("running simulation");
        runSimButton.setDisable(true);

        SimController.runSimulations();
    }

    public void handleImportSettings(ActionEvent actionEvent) {
        Settings.importSettings((Stage) home.getScene().getWindow());
    }

    public void handleExportSettings(ActionEvent actionEvent) {
        Settings.exportSettings((Stage) home.getScene().getWindow());
    }

    public void inflateOrderDistribution() {
        for (Integer numOrders : Settings.getOrderDistribution()) {
            addHour(numOrders);
        }
    }

    public void addHour(Integer numOrders) {
        Text hourTitle = new Text(Integer.toString(gridIndex));
        hourTitle.getStyleClass().add("hourTitle");

        TextField orderField = new TextField();
        orderField.setText(Integer.toString(numOrders));
        orderField.getStyleClass().add("orderField");
        int distIndex = gridIndex -1;
        orderField.setOnAction(actionEvent -> {
            // TODO: sanitize input
            Settings.editDistribution(distIndex, Integer.parseInt(orderField.getText()));
        });

        GridPane.setMargin(hourTitle, new Insets(15, 0, 0, 0));
        GridPane.setMargin(orderField, new Insets(15, 0, 0, 40));

        contentGrid.add(hourTitle, 0, gridIndex);
        contentGrid.add(orderField, 1, gridIndex);

        gridIndex++;
    }

    public void updateRunBtn(String errMessage, boolean valid) {
        if (valid) {
            runSimButton.setStyle("-fx-background-color: #0078D7");
            runSimButton.setText("Run");
            runSimButton.setDisable(false);
        } else {
            runSimButton.setStyle("-fx-background-color: #EC2F08");
            runSimButton.setText(errMessage);
            runSimButton.setDisable(true);
        }
    }
}
