/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the Drone FXML document
 */

package gui.controllers;

import cli.SimController;
import cli.SimulationThread;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.stage.Stage;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Drone implements Initializable {
    public ScrollPane scrollpane;
    public GridPane contentGrid;
    public Text hoursTitle;
    public Text ordersTitle;
    public VBox settingButtons;
    public Button importSettingsButton;
    public Button exportSettingsButton;
    public VBox runBtnVbox;
    public Button runSimButton;
    public Text weightTitle;
    public Text flightSpeedTitle;
    public Text carryingCapacityTitle;
    public Text maxFlightTimeTitle;
    public Text turnAroundTime;
    public Text deliveryTimeTitle;
    public Text turnAroundTimeTitle;
    public TextField deliveryTimeInput;
    public TextField turnAroundTimeInput;
    public TextField maxFlightTimeInput;
    public TextField carryingCapacityInput;
    public TextField flightSpeedInput;
    public TextField weightInput;
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
    public ImageView downloadImage;
    public ImageView uploadImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");
        drone.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        inflateSettings();

        loadIcons();
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

    public void inflateSettings() {
        double weight = Settings.getDrone().getWeight();
        double speed = Settings.getDrone().getSpeed();
        double maxFlightTime = Settings.getDrone().getMaxFlightTime();
        double turnaroundTime = Settings.getDrone().getTurnaroundTime();
        double deliveryTime = Settings.getDrone().getDeliveryTime();

        System.out.println(Double.toString(weight));

        weightInput.setText(Double.toString(weight));
        flightSpeedInput.setText(Double.toString(speed));
        maxFlightTimeInput.setText(Double.toString(maxFlightTime));
        turnAroundTimeInput.setText(Double.toString(turnaroundTime));
        deliveryTimeInput.setText(Double.toString(deliveryTime));
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root, "MealItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "Map", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Drone");
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {

    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }

    public void handleImportSettings(ActionEvent actionEvent) {
        Settings.importSettings((Stage) home.getScene().getWindow());
    }

    public void handleExportSettings(ActionEvent actionEvent) {
        Settings.exportSettings((Stage) home.getScene().getWindow());
    }

    public void handleRunSimulation(ActionEvent actionEvent) {
        ProgressBar pb = new ProgressBar();
        try {
            if (SimController.simRan) {
                Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
                Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
                Navigation.pushScene("OrderDistribution");
                return;
            }
            SimulationThread simulationThread = new SimulationThread();
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

    public void handleUpdateWeight(ActionEvent actionEvent) {
        Settings.getDrone().setWeight(Double.parseDouble(weightInput.getText()));
    }

    public void handleUpdateFlightSpeed(ActionEvent actionEvent) {
        Settings.getDrone().setSpeed(Double.parseDouble(flightSpeedInput.getText()));
    }

    public void handleUpdateCarryingCapacity(ActionEvent actionEvent) {
//        Settings.getDrone().(Double.parseDouble(weightInput.getText()));
    }

    public void handleUpdateMaxFlightTime(ActionEvent actionEvent) {
        Settings.getDrone().setMaxFlightTime(Double.parseDouble(maxFlightTimeInput.getText()));
    }

    public void handleUpdateTurnAroundTime(ActionEvent actionEvent) {
        Settings.getDrone().setTurnaroundTime(Double.parseDouble(turnAroundTimeInput.getText()));
    }

    public void handleUpdateDeliveryTime(ActionEvent actionEvent) {
        Settings.getDrone().setDeliveryTime(Double.parseDouble(deliveryTimeInput.getText()));
    }
}
