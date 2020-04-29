/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the MealItems FXML document
 */

package gui.controllers;

import cli.SimulationThread;
import cli.SimController;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menu.FoodItem;
import menu.Meal;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealItems implements Initializable {
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
    public Button addMeal;
    public Button importSettingsButton;
    public Button exportSettingsButton;
    public Button runSimButton;
    public VBox settingButtons;
    public ScrollPane scrollpane;
    public VBox mealsVBox;
    public ImageView uploadImage;
    public ImageView downloadImage;
    public VBox runBtnVbox;

    private int gridIndex;
    private ArrayList invalidFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");
        mealItems.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        SimController.setCurrentButton(runSimButton);

        VBox.setMargin(addMeal, new Insets(0, 0, 300, 0));

        invalidFields = new ArrayList();
        gridIndex = 1;
        loadIcons();
        inflateMeals();
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

        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        if (SimController.resultsLock) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner((Stage) home.getScene().getWindow());
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("A simulation has to be run\n and finish executing before\n you can navigate to the results page"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
            Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow(), invalidFields);
            Navigation.pushScene("MealItems");
        }
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "Map", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow(), invalidFields);
        Navigation.pushScene("MealItems");
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

    public void handleRunSimulation(ActionEvent actionEvent) throws IOException {
        ProgressBar pb = new ProgressBar();
        try {
            if (SimController.simRan) {
                Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
                Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
                Navigation.pushScene("MealItems");
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

    public void bindMealName(TextField field, Meal meal) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            meal.setName(newValue);
        });
    }

    public void bindMealDistribution(TextField field, Meal meal) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double distribution = Double.parseDouble(newValue);
                if (!Settings.isValidMealDistribution(distribution)) {
                    throw new Exception("bad distribution");
                }
                meal.setDistribution((float) distribution);
                field.setStyle("-fx-border-width: 0 0 0 0;");
                invalidFields.remove(meal);
                if (invalidFields.isEmpty()) {
                    Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
                } else {
                    Navigation.updateRunBtn(runSimButton, "Invalid meal distributions");
                }
            } catch (Exception e) {
                field.setStyle("-fx-border-color: red;" + "-fx-border-width: 2px 2px 2px 2px");
                Navigation.updateRunBtn(runSimButton, "Invalid meal distributions");
                if (!invalidFields.contains(meal)) {
                    invalidFields.add(meal);
                }
            }
        });
    }

    public void inflateMeals() {
        Set<Meal> meals = new HashSet<>();
        meals.addAll(Settings.getMeals());
        for (Meal meal : meals) {
            addMeal(meal);
        }
    }

    public void handleAddMeal(ActionEvent actionEvent) {
        addMeal(new Meal("Meal Name", gridIndex - 1));
    }

    public void addMeal(Meal meal) {
        GridPane mealGrid = new GridPane();
        VBox.setMargin(mealGrid, new Insets(50, 0, 15, 0));

        GridPane controlGrid = new GridPane();
        controlGrid.setHgap(15);
        controlGrid.setVgap(15);
        VBox.setMargin(controlGrid, new Insets(0, 0, 60, 0));

        Text mealTitle = new Text("Meal Name:");
        mealTitle.getStyleClass().add("mealTitle");

        TextField mealName = new TextField(meal.getName());
        mealName.getStyleClass().add("mealName");
        bindMealName(mealName, meal);

        Text mealWeight = new Text("Weight (oz): ");
        mealWeight.getStyleClass().add("weightTitle");

        Text weight = new Text(Float.toString(meal.getWeight()));
        weight.getStyleClass().add("weight");

        mealGrid.add(mealTitle, 0, 0);
        mealGrid.add(mealName, 1, 0);
        GridPane.setMargin(mealName, new Insets(0, 0, 0, 34));

        Text distributionTitle = new Text("Distribution(%):");
        distributionTitle.getStyleClass().add("distributionTitle");

        TextField distribution = new TextField(Float.toString(meal.getDistribution()));
        distribution.getStyleClass().add("distribution");
        bindMealDistribution(distribution, meal);
        GridPane.setMargin(distribution, new Insets(0, 0, 0, -7));


        Button addItemBtn = new Button();
        addItemBtn.setText("Add Item");
        addItemBtn.getStyleClass().add("smallGrayButton");
        addItemBtn.setOnAction(actionEvent -> {
            FoodItem newFoodItem = meal.getOutstandingFoodItems().get(0);
            meal.incrementFoodItem(newFoodItem, 0);
            addFood(mealGrid, newFoodItem, meal, weight, addItemBtn, 0);
            // TODO: update settings
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
            if (meal.getOutstandingFoodItems().isEmpty()) {
                addItemBtn.setDisable(true);
                addItemBtn.setVisible(false);
            }
        });
        if (meal.getOutstandingFoodItems().isEmpty()) {
            addItemBtn.setDisable(true);
            addItemBtn.setVisible(false);
        }

        Button deleteMealBtn = new Button();
        deleteMealBtn.setText("Remove Meal");
        deleteMealBtn.getStyleClass().add("smallDeleteButton");
        deleteMealBtn.setOnAction(actionEvent -> {
            mealsVBox.getChildren().remove(mealGrid);
            mealsVBox.getChildren().remove(controlGrid);
            Settings.removeMeal(meal);
            invalidFields.remove(meal);
        });

        controlGrid.add(distributionTitle, 0, 0);
        controlGrid.add(distribution, 1, 0);
        controlGrid.add(addItemBtn, 3, 0);
        controlGrid.add(deleteMealBtn, 3, 1);
        controlGrid.add(mealWeight, 0, 1);
        controlGrid.add(weight, 1, 1);

        meal.getFoodItems().forEach((key, value) -> {
            addFood(mealGrid, key, meal, weight, addItemBtn, value);
        });

        mealsVBox.getChildren().add(mealGrid);
        mealsVBox.getChildren().add(controlGrid);

        if (!Settings.getMeals().contains(meal)) {
            Settings.addMeal(meal);
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings() );
        }
        else {
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
        }
    }

    public void addFood(GridPane grid, FoodItem food, Meal meal, Text weight, Button addItemBtn, int num) {
        MenuButton foodName = new MenuButton();
        foodName.setText(food.getName());
        foodName.getStyleClass().add("foodName");
//        foodName.setPrefWidth(200);

        foodName.setOnMouseEntered(actionEvent -> {
            updateMenuItems(foodName, meal, food, weight, addItemBtn);
        });

        Text number = new Text(String.valueOf(meal.getNumberOfFood(food)));
        number.getStyleClass().add("foodNumber");

        // add icons to increase and decrease
        File increasePath = new File("assets/icons/plus.png");
        Image increaseImage = new Image(increasePath.toURI().toString());
        ImageView plus = new ImageView(increaseImage);
        plus.setFitHeight(15);
        plus.setFitWidth(15);
        plus.setPreserveRatio(true);
        Button increase = new Button("", plus);
        increase.getStyleClass().add("removeButton");
        increase.setOnAction(actionEvent -> {
            meal.incrementFoodItem(food);
            weight.setText(String.valueOf(meal.getWeight()));
            number.setText(String.valueOf((Integer.parseInt(number.getText()) + 1)));
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
        });

        File decreasePath = new File("assets/icons/minus.png");
        Image decreaseImage = new Image(decreasePath.toURI().toString());
        ImageView minus = new ImageView(decreaseImage);
        minus.setFitWidth(15);
        minus.setFitHeight(15);
        minus.setPreserveRatio(true);
        Button decrease = new Button("", minus);
        decrease.getStyleClass().add("removeButton");
        decrease.setOnAction(actionEvent -> {
            meal.decrementFoodItem(food, 1);
            weight.setText(String.valueOf(meal.getWeight()));
            number.setText(String.valueOf(meal.getNumberOfFood(food)));
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
        });


        File deleteMealPath = new File("assets/icons/remove.png");
        Image deleteMealImage = new Image(deleteMealPath.toURI().toString());
        ImageView icon = new ImageView(deleteMealImage);
        icon.setFitHeight(15);
        icon.setFitWidth(15);
        icon.setPreserveRatio(true);
        Button removeMeal = new Button("", icon);
        removeMeal.getStyleClass().add("removeButton");

        removeMeal.setOnAction(actionEvent -> {
            // remove fields
            grid.getChildren().remove(foodName);
            grid.getChildren().remove(number);
            grid.getChildren().remove(removeMeal);
            grid.getChildren().remove(increase);
            grid.getChildren().remove(decrease);

            meal.removeFoodItem(food);
            weight.setText(String.valueOf(meal.getWeight()));
            // TODO: update run button
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());

            // enable addItemButton
            addItemBtn.setDisable(false);
            addItemBtn.setVisible(true);
        });
        grid.add(foodName, 1, gridIndex);
        grid.add(number, 2, gridIndex);
        grid.add(decrease, 3, gridIndex);
        grid.add(increase, 4, gridIndex);
        grid.add(removeMeal, 5, gridIndex);

        GridPane.setMargin(foodName, new Insets(8, 0, 0, 35));
        GridPane.setMargin(number, new Insets(8, 0, 0, 34));
        GridPane.setMargin(removeMeal, new Insets(8, 0, 0, 15));
        GridPane.setMargin(decrease, new Insets(8, 0, 0, 0));
        GridPane.setMargin(increase, new Insets(8, 0, 0, 0));

        gridIndex++;
        Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
    }

    public void updateMenuItems(MenuButton dropdown, Meal meal, FoodItem food, Text weight, Button addItemBtn) {
        dropdown.getItems().clear();
        for (FoodItem item : meal.getOutstandingFoodItems()) {
            MenuItem menuItem = new MenuItem(item.getName());
            dropdown.getItems().add(menuItem);
            menuItem.setOnAction(actionEvent -> {
                updateMenuItems(dropdown, meal, food, weight, addItemBtn);
                dropdown.setText(item.getName() + ": " + Float.toString(item.getWeight()) + " oz");
                meal.replaceFoodItem(food, item);
                weight.setText(String.valueOf(meal.getWeight()));
                if (meal.getOutstandingFoodItems().isEmpty()) {
                    addItemBtn.setDisable(true);
                    addItemBtn.setVisible(false);
                }
                updateMenuItems(dropdown, meal, food, weight, addItemBtn);
            });
        }
        if (invalidFields.isEmpty()) {
            Navigation.updateRunBtn(runSimButton, Settings.verifySettings());
        } else {
            Navigation.updateRunBtn(runSimButton, "Invalid meal distributions");
        }
    }
}
