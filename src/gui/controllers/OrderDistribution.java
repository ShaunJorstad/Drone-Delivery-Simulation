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
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public VBox runBtnVbox;

    private int gridIndex;
    private ArrayList invalidFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        orderDistribution.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        GridPane.setMargin(ordersTitle, new Insets(0, 0, 0, 40));
        gridIndex = 1;
        invalidFields = new ArrayList();

        SimController.setCurrentButton(runSimButton);

        loadIcons();
        injectCursorStates();
        inflateOrderDistribution();
        checkSimulationStatus();
        Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
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
        Navigation.inflateScene(root,"OrderDistribution", "Splash", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"OrderDistribution", "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root,"OrderDistribution", "Results", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "OrderDistribution","FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root,"OrderDistribution", "MealItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root,"OrderDistribution", "Map", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root,"OrderDistribution", "Drone", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.peekScene(); // TODO: this needs to be put back on the stack if user hits cancel,
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.navigateBack(root, lastScene, (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleRunSimulation(ActionEvent actionEvent) throws IOException {
        ProgressBar pb = new ProgressBar();
        try {
            if (SimController.simRan) {
                Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
                Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
                Navigation.pushScene("OrderDistribution");
                return;
            }
            SimulationThread simulationThread = new SimulationThread();
            SimController.clearResults();
            simulationThread.setOnRunning((successEvent) -> {
                runSimButton.setStyle("-fx-background-color: #1F232F");
                runSimButton.setText("running simulation");
                runSimButton.setDisable(true);

                pb.progressProperty().bind(simulationThread.progressProperty());
                runBtnVbox.getChildren().add(pb);
            });

            simulationThread.setOnSucceeded((successEvent) -> {
                SimController.getCurrentButton().setText("view results");
                SimController.getCurrentButton().setStyle("-fx-background-color: #0078D7");
                SimController.getCurrentButton().setDisable(false);
                SimController.simInProgress = false;
                runBtnVbox.getChildren().remove(pb);
            });

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(simulationThread);
            executorService.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void handleImportSettings(ActionEvent actionEvent) throws IOException {
        Settings.importSettings((Stage) home.getScene().getWindow());
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow());
    }

    public void handleExportSettings(ActionEvent actionEvent) {
        Settings.exportSettings((Stage) home.getScene().getWindow());
    }

    public void inflateOrderDistribution() {
        for (Integer numOrders : Settings.getOrderDistribution()) {
            addHour(numOrders);
        }
    }

    public void bindOrders(TextField field, int index) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int numOrders = Integer.parseInt(newValue);
                if (numOrders < 0) {
                    throw new Exception("invalid number of orders");
                }
                Settings.editDistribution(index, numOrders);
                field.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove(Integer.valueOf(index));
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid distribution of orders");
                }
            } catch (Exception e) {
                field.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Inavlid distribution of orders");
                if (!invalidFields.contains(index)) {
                    invalidFields.add(Integer.valueOf(index));
                }
            }
        });
    }

    public void addHour(Integer numOrders) {
        Text hourTitle = new Text(Integer.toString(gridIndex));
        hourTitle.getStyleClass().add("hourTitle");

        TextField orderField = new TextField();
        orderField.setText(Integer.toString(numOrders));
        orderField.getStyleClass().add("orderField");
        int distIndex = gridIndex -1;
        bindOrders(orderField, distIndex);

        GridPane.setMargin(hourTitle, new Insets(15, 0, 0, 0));
        GridPane.setMargin(orderField, new Insets(15, 0, 0, 40));

        contentGrid.add(hourTitle, 0, gridIndex);
        contentGrid.add(orderField, 1, gridIndex);

        gridIndex++;
    }
}
