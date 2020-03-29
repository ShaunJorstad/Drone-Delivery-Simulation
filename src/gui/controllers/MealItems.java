/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the MealItems FXML document
 */

package gui.controllers;

import cli.SimController;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import menu.FoodItem;
import menu.Meal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MealItems implements Initializable {
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

    private int mealCount;
    private int gridIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        mealItems.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");


        // sets scrolling settings
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);

        VBox.setMargin(addMeal, new Insets(0, 0, 300, 0));

        loadIcons();
        injectCursorStates();

        gridIndex = 1;
        inflateMeals();
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

        uploadImage.setImage(new Image(new File("assets/icons/upload.png").toURI().toString()));
        downloadImage.setImage(new Image(new File("assets/icons/download.png").toURI().toString()));
    }

    public void injectCursorStates() {
        List<Button> items = Arrays.asList(home, settings, results, back, runSimButton, foodItems, mealItems, orderDistribution, map, drone, importSettingsButton, exportSettingsButton, addMeal);
        for (Button item : items) {
            item.setOnMouseEntered(mouseEvent -> {
                item.getScene().setCursor(Cursor.HAND);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }
    public void injectCursorStates(Button btn) {
        btn.setOnMouseEntered(mouseEvent -> {
            btn.getScene().setCursor(Cursor.HAND);
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    public void injectCursorStates(MenuButton btn) {
        btn.setOnMouseEntered(mouseEvent -> {
            btn.getScene().setCursor(Cursor.HAND);
        });
        btn.setOnMouseExited(mouseEvent -> {
            btn.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "Map", (Stage) home.getScene().getWindow());
        Navigation.pushScene("MealItems");
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow());
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
    }

    public void handleExportSettings(ActionEvent actionEvent) {
    }

    public void handleRunSimulation(ActionEvent actionEvent) {
    }

    public void inflateMeals() {
        for (Meal meal : SimController.getDefaultFood().getMeals()) {
            addMeal(meal);
        }
    }

    public void handleAddMeal(ActionEvent actionEvent) {
        addMeal(new Meal("Meal Name", mealCount));
    }

    public void addMeal(Meal meal) {
        GridPane mealGrid = new GridPane();
        VBox.setMargin(mealGrid, new Insets(0, 0, 15, 0));

        GridPane controlGrid = new GridPane();
        controlGrid.setHgap(15);
        controlGrid.setVgap(15);
        VBox.setMargin(controlGrid, new Insets(0, 0, 60, 0));

        Text mealTitle = new Text("Meal Name:");
        mealTitle.getStyleClass().add("mealTitle");

        TextField mealName = new TextField(meal.getName());
        mealName.getStyleClass().add("mealName");
        mealName.setOnAction(actionEvent -> {
            // update name within settings
        });

        Text mealWeight = new Text("Weight (lbs): ");
        mealWeight.getStyleClass().add("weightTitle");

        Text weight = new Text(Float.toString(meal.getWeight()));
        weight.getStyleClass().add("weight");

        mealGrid.add(mealTitle, 0, 0);
        mealGrid.add(mealName, 1, 0);
        GridPane.setMargin(mealName, new Insets(0, 0, 0, 34));

        Text distributionTitle = new Text("Distribution:");
        distributionTitle.getStyleClass().add("distributionTitle");

        TextField distribution = new TextField("% " + Float.toString(meal.getDistribution()));
        distribution.getStyleClass().add("distribution");
        distribution.setOnAction(actionEvent -> {
            // update menu item in settings
            // check settings for correctness
        });

        Button addItemBtn = new Button();
        addItemBtn.setText("Add Item");
        addItemBtn.getStyleClass().add("smallGrayButton");
        addItemBtn.setOnAction(actionEvent -> {
            // TODO: add item handler
            addFood(mealGrid, new FoodItem("", 0), 0);
            // TODO: re-evaluate distributions and stuff
            // TODO: update settings
        });
        injectCursorStates(addItemBtn);

        Button deleteMealBtn = new Button();
        deleteMealBtn.setText("Remove Meal");
        deleteMealBtn.getStyleClass().add("smallDeleteButton");
        deleteMealBtn.setOnAction(actionEvent -> {
            mealsVBox.getChildren().remove(mealGrid);
            mealsVBox.getChildren().remove(controlGrid);
            // TODO: update settings
            // TODO: check for correct settings (distributions)
        });
        injectCursorStates(deleteMealBtn);

        controlGrid.add(distributionTitle, 0, 0);
        controlGrid.add(distribution, 1, 0);
        controlGrid.add(addItemBtn, 3, 0);
        controlGrid.add(deleteMealBtn, 3, 1);
        controlGrid.add(mealWeight, 0, 1);
        controlGrid.add(weight, 1, 1);

        meal.getFoodItems().forEach((key, value) -> {
            addFood(mealGrid, key, value);
        });

        mealsVBox.getChildren().add(mealGrid);
        mealsVBox.getChildren().add(controlGrid);
    }

    public void addFood(GridPane grid, FoodItem food, int num) {
        MenuButton foodName = new MenuButton();
        foodName.setText(food.getName());
        foodName.getStyleClass().add("foodName");
        foodName.setPrefWidth(200);
        injectCursorStates(foodName);
        // add all food options
        for (FoodItem item : SimController.getDefaultFood().getFoods()) {
            MenuItem menuItem = new MenuItem(item.getName());
            foodName.getItems().add(menuItem);
            menuItem.setOnAction(actionEvent -> {
                foodName.setText(item.getName() + ": " + Float.toString(item.getWeight()) + " oz");
                // update menu item in settings
                // re evaluate weight
                // check weight under limit
            });
        }

        TextField number = new TextField(Integer.toString(num));
        number.getStyleClass().add("foodNumber");
        number.setOnAction(actionEvent -> {
            // update settings
            // update weight attribute on meal
            // verify settings are acurate
        });

        File deleteMealPath = new File("assets/icons/remove.png");
        Image deleteMealImage = new Image(deleteMealPath.toURI().toString());
        ImageView icon = new ImageView(deleteMealImage);
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        icon.setPreserveRatio(true);

        Button removeMeal = new Button("", icon);
        removeMeal.getStyleClass().add("removeButton");
        removeMeal.setOnAction(actionEvent -> {
            // remove fields
            grid.getChildren().remove(foodName);
            grid.getChildren().remove(number);
            grid.getChildren().remove(removeMeal);

            // TODO: delete from settings
//                TODO: re calculate weiht
            /* code */
        });
        injectCursorStates(removeMeal);

        grid.add(foodName, 2, gridIndex);
        grid.add(number, 1, gridIndex);
        grid.add(removeMeal, 3, gridIndex);

        GridPane.setMargin(foodName, new Insets(15, 0, 0, 30));
        GridPane.setMargin(number, new Insets(15, 0, 0, 34));
        GridPane.setMargin(removeMeal, new Insets(15, 0, 0, 15));
        gridIndex++;
    }
}
