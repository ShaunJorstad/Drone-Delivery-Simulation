/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the FoodItems FXML document
 */

package gui.controllers;

import cli.SimController;
import cli.SimulationThread;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.FoodItem;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodItems implements Initializable {

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
    public Button runSimButton;
    public Button back;
    public ImageView backImage;
    public Button importSettingsButton;
    public Button exportSettingsButton;
    public VBox settingButtons;
    public ScrollPane scrollpane;
    public Text nameTitle;
    public GridPane contentGrid;
    public Text weightTitle;
    public Button addFood;
    public ImageView uploadImage;
    public ImageView downloadImage;
    public VBox runBtnVbox;

    private int gridIndex;
    private ArrayList invalidFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");
        foodItems.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        SimController.setCurrentButton(runSimButton);

        invalidFields = new ArrayList();
        gridIndex = 1;
        constructTooltips();
        loadIcons();
        inflateFoodItems();
        Navigation.updateRunBtn(runSimButton, Settings.verifySettings(), invalidFields);
    }

    /**
     * loads the icons for the backbutton and import/export buttons
     */
    public void loadIcons() {
        File backFile;
        if (Navigation.isEmpty()) {
            backFile = new File("assets/icons/backGray.png");
        } else {
            backFile = new File("assets/icons/backBlack.png");
        }

        VBox.setMargin(addFood, new Insets(0, 0, 500, 0));

        Image backArrowImage = new Image(backFile.toURI().toString());
        backImage.setImage(backArrowImage);
        backImage.setFitHeight(16);
        backImage.setFitWidth(16);
        backImage.setPreserveRatio(true);

        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        uploadImage.setImage(new Image(new File("assets/icons/upload.png").toURI().toString()));
        downloadImage.setImage(new Image(new File("assets/icons/download.png").toURI().toString()));
    }

    /**
     * creates the tooltips for the buttons
     */
    public void constructTooltips() {
        addFood.setTooltip(new Tooltip("Adds a new food item to be ordered"));

        runSimButton.setTooltip(new Tooltip("Runs the simulation if settings are valid"));
        importSettingsButton.setTooltip(new Tooltip("Imports settings from a local file"));
        exportSettingsButton.setTooltip(new Tooltip("Exports current settings to a local file"));
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "FoodItems", "Splash", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "FoodItems", "Results", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", "MealItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "FoodItems", "OrderDistribution", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "FoodItems", "Map", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "FoodItems", "Drone", (Stage) home.getScene().getWindow(), invalidFields);
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
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
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
                Navigation.pushScene("FoodItems");
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

    /**
     * Binds the provided textField with the provided foodItem in Settings.
     * Any update to 'field' that is valid, will automatically update 'foodItem'
     *
     * @param field
     * @param foodItem
     */
    public void bindName(TextField field, FoodItem foodItem) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            foodItem.setName(newValue);
            Settings.editFoodItem(foodItem);
        });
    }

    /**
     * Binds the provided weight text field to the provided food item
     * any updates to 'field' that are a valid weight are automatically updated to 'fooditem'
     *
     * @param field
     * @param foodItem
     */
    public void bindWeight(TextField field, FoodItem foodItem) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double newWeight = Double.parseDouble(newValue);
                if (!Settings.isValidFoodWeight(newWeight)) {
                    throw new Exception("invalid food weight");
                }
                foodItem.setWeight((float) newWeight);
                Settings.editFoodItem(foodItem);
                field.setStyle("-fx-border-width: 0 0 0 0;");

                invalidFields.remove(foodItem);
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid food weights");
                }
            } catch (Exception e) {
                field.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Inavlid food weight");
                if (!invalidFields.contains(foodItem)) {
                    invalidFields.add(foodItem);
                }
            }
        });
    }

    /**
     * inflates the foodItems in Settings onto the gui
     */
    public void inflateFoodItems() {
//        load food items from settings object
        for (FoodItem item : Settings.getFoods()) {
            TextField nameInput = new TextField(item.getName());
            TextField weightInput = new TextField(Float.toString(item.getWeight()));

            nameInput.getStyleClass().add("foodName");
            nameInput.setTooltip(new Tooltip("Name of the food item. This CAN be the same as other foods"));
            bindName(nameInput, item);

            weightInput.getStyleClass().add("foodWeight");
            bindWeight(weightInput, item);
            weightInput.setTooltip(new Tooltip("Weight of the food\n(positive foating point number required)"));

            // margins
            Insets one = new Insets(15, 0, 0, 0);
            Insets two = new Insets(15, 0, 0, 0);
            Insets three = new Insets(15, 0, 0, 0);

            File removeFoodIconFile = new File("assets/icons/remove.png");
            Image removeFoodIcon = new Image(removeFoodIconFile.toURI().toString());
            ImageView icon = new ImageView(removeFoodIcon);
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            icon.setPreserveRatio(true);
            Button removeFood = new Button("", icon);
            removeFood.getStyleClass().add("removeButton");
            removeFood.setTooltip(new Tooltip("removes food from settings. This may break your meal items"));
            removeFood(item, nameInput, weightInput, removeFood);

//        add button
            contentGrid.add(nameInput, 0, gridIndex);
            contentGrid.add(weightInput, 1, gridIndex);
            contentGrid.add(removeFood, 2, gridIndex);

            GridPane.setMargin(nameInput, one);
            GridPane.setMargin(weightInput, two);
            GridPane.setMargin(removeFood, three);
            gridIndex++;
        }

    }

    /**
     * adds a new foodItem to the gui and settings
     *
     * @param actionEvent
     */
    public void handleAddFoodItem(ActionEvent actionEvent) {
//        insert new fields into table and change run button to red
        FoodItem newFood = new FoodItem("food name", 0);

        TextField nameInput = new TextField("food name");
        TextField weightInput = new TextField("0");

        nameInput.getStyleClass().add("foodName");
        nameInput.setTooltip(new Tooltip("Name of the food item. This CAN be the same as other foods"));
        bindName(nameInput, newFood);

        weightInput.getStyleClass().add("foodWeight");
        bindWeight(weightInput, newFood);
        weightInput.setTooltip(new Tooltip("Weight of the food\n(positive foating point number required)"));

        // margins
        Insets one = new Insets(15, 0, 0, 0);
        Insets two = new Insets(15, 0, 0, 0);
        Insets three = new Insets(15, 0, 0, 0);

        File removeFoodIconFile = new File("assets/icons/remove.png");
        Image removeFoodIcon = new Image(removeFoodIconFile.toURI().toString());
        ImageView icon = new ImageView(removeFoodIcon);
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        icon.setPreserveRatio(true);
        Button removeFood = new Button("", icon);
        removeFood.getStyleClass().add("removeButton");

        removeFood(newFood, nameInput, weightInput, removeFood);
        removeFood.setTooltip(new Tooltip("removes food from settings. This may break your meal items"));

        contentGrid.add(nameInput, 0, gridIndex);
        contentGrid.add(weightInput, 1, gridIndex);
        contentGrid.add(removeFood, 2, gridIndex);

        GridPane.setMargin(nameInput, one);
        GridPane.setMargin(weightInput, two);
        GridPane.setMargin(removeFood, three);
        gridIndex++;

        //insert item into settings
        Settings.addFoodItem(newFood);
        Navigation.updateRunBtn(runSimButton, Settings.verifySettings(), invalidFields);
    }

    /**
     * removes the fooditem from the gui and settings, followed up updating the run button
     * @param newFood
     * @param nameInput
     * @param weightInput
     * @param removeFood
     */
    private void removeFood(FoodItem newFood, TextField nameInput, TextField weightInput, Button removeFood) {
        removeFood.setOnAction(actionEvent1 -> {
            // remove fields
            contentGrid.getChildren().remove(nameInput);
            contentGrid.getChildren().remove(weightInput);
            contentGrid.getChildren().remove(removeFood);

            invalidFields.remove(newFood);
            Settings.removeFoodItem(newFood);
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings(), invalidFields);
        });
    }
}
