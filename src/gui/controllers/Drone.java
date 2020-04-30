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
import javafx.scene.Scene;
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
    public TextField fleetSizeInput;
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

    private ArrayList invalidFields;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");
        drone.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        SimController.setCurrentButton(runSimButton);
        invalidFields = new ArrayList();

        bindTextFields();
        inflateSettings();
        loadIcons();
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

    public void inflateSettings() {
        double weight = Settings.getDrone().getWeight();
        double speed = Settings.getDrone().getSpeed();
        double maxFlightTime = Settings.getDrone().getMaxFlightTime();
        double turnaroundTime = Settings.getDrone().getTurnaroundTime();
        double deliveryTime = Settings.getDrone().getDeliveryTime();
        int fleetSize = Settings.getDroneFleetSize();

        weightInput.setText(Double.toString(weight));
        flightSpeedInput.setText(Double.toString(speed));
        maxFlightTimeInput.setText(Double.toString(maxFlightTime));
        turnAroundTimeInput.setText(Double.toString(turnaroundTime));
        deliveryTimeInput.setText(Double.toString(deliveryTime));
        fleetSizeInput.setText(Integer.toString(fleetSize));
    }

    public void bindTextFields() {
        weightInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double val = Double.parseDouble(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.getDrone().setWeight(val);
                weightInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("weight");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                weightInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid drone weight");
                if(!invalidFields.contains("weight")) {
                    invalidFields.add("weight");
                }
            }
        });

        flightSpeedInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double val = Double.parseDouble(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.getDrone().setSpeed(val);
                flightSpeedInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("flight speed");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                flightSpeedInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid drone flight speed");
                if(!invalidFields.contains("flight speed")) {
                    invalidFields.add("flight speed");
                }
            }
        });

        maxFlightTimeInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double val = Double.parseDouble(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.getDrone().setMaxFlightTime(val);
                maxFlightTimeInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("max flight time");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                maxFlightTimeInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid drone max flight time");
                if(!invalidFields.contains("max flight time")) {
                    invalidFields.add("max flight time");
                }
            }
        });

        turnAroundTimeInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double val = Double.parseDouble(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.getDrone().setTurnaroundTime(val);
                turnAroundTimeInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("turn around time");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                turnAroundTimeInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid drone turn around time");
                if (!invalidFields.contains("turn around time")) {
                    invalidFields.add("turn around time");
                }
            }
        });

        deliveryTimeInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double val = Double.parseDouble(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.getDrone().setDeliveryTime(val);
                deliveryTimeInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("delivery time");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                deliveryTimeInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid drone delivery time");
                if (!invalidFields.contains("delivery time")) {
                    invalidFields.add("delivery time");
                }
            }
        });

        fleetSizeInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int val = Integer.parseInt(newValue);
                if (val < 0) {
                    throw new Exception("cannot be negative");
                }
                Settings.setDroneFleetSize(val);
                fleetSizeInput.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove("fleet size");
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid " + invalidFields.get(0));
                }
            } catch (Exception e) {
                fleetSizeInput.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid fleet size");
                if (!invalidFields.contains("fleet size")) {
                    invalidFields.add("fleet size");
                }
            }
        });
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Drone", "Splash", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"Drone", "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Drone","Results", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"Drone", "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root,"Drone", "MealItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root,"Drone", "OrderDistribution", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root,"Drone", "Map", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {

    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.peekScene(); // TODO: this needs to be put back on the stack if user hits cancel,
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.navigateBack(root, lastScene, (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleImportSettings(ActionEvent actionEvent) throws IOException {
        Settings.importSettings((Stage) home.getScene().getWindow());
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow());
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
}
