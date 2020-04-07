/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the FoodItems FXML document
 */

package gui.controllers;

import cli.SimController;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
import menu.FoodItem;
import simulation.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private int gridIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");
        foodItems.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        SimController.setCurrentButton(runSimButton);

        gridIndex = 1;
        loadIcons();
        inflateFoodItems();
    }

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

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root, "MealItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Navigation.inflateScene(root, "Map", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow());
        Navigation.pushScene("FoodItems");
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
        runSimButton.setStyle("-fx-background-color: #1F232F");
        runSimButton.setText("running simulation");
        runSimButton.setDisable(true);

        SimController.runSimulations();
    }

    public void inflateFoodItems() {
//        load food items from settings object
        for (FoodItem item : Settings.getFoods()) {
            TextField nameInput = new TextField(item.getName());
            TextField weightInput = new TextField(Float.toString(item.getWeight()));

            nameInput.getStyleClass().add("foodName");
            nameInput.setOnAction(actionEvent -> {
                item.setName(nameInput.getText());
                item.setWeight(Float.parseFloat(weightInput.getText()));
                //updateRunBtn("Invalid food weight", Settings.editFoodItem(item));
            });

            weightInput.getStyleClass().add("foodWeight");
            weightInput.setOnAction(actionEvent -> {
                item.setName(nameInput.getText());
                item.setWeight(Float.parseFloat(weightInput.getText()));
                Settings.updateMeals(item);
                //TODO: fix this. don't need to set the item
                updateRunBtn("Invalid food weight", Settings.editFoodItem(item));
            });

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
            removeFood.setOnAction(actionEvent -> {
                // remove fields
                contentGrid.getChildren().remove(nameInput);
                contentGrid.getChildren().remove(weightInput);
                contentGrid.getChildren().remove(removeFood);

                updateRunBtn("Not enough Food items", Settings.removeFoodItem(item));
            });

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

    public void handleAddFoodItem(ActionEvent actionEvent) {
//        insert new fields into table and change run button to red
        FoodItem newFood = new FoodItem("food name", 0);

        TextField nameInput = new TextField("food name");
        TextField weightInput = new TextField("0");

        nameInput.getStyleClass().add("foodName");
        nameInput.setOnAction(actionEvent12 -> {
            newFood.setName(nameInput.getText());
            newFood.setWeight(Float.parseFloat(weightInput.getText()));
            updateRunBtn("Invalid food weight", Settings.editFoodItem(newFood));
        });

        weightInput.getStyleClass().add("foodWeight");
        weightInput.setOnAction(actionEvent13 -> {
            newFood.setName(nameInput.getText());
            newFood.setWeight(Float.parseFloat(weightInput.getText()));
            updateRunBtn("Invalid Food Weight", Settings.editFoodItem(newFood));
        });

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

        removeFood.setOnAction(actionEvent1 -> {
            // remove fields
            contentGrid.getChildren().remove(nameInput);
            contentGrid.getChildren().remove(weightInput);
            contentGrid.getChildren().remove(removeFood);

            updateRunBtn("Not enough Food items", Settings.removeFoodItem(newFood));
        });

        contentGrid.add(nameInput, 0, gridIndex);
        contentGrid.add(weightInput, 1, gridIndex);
        contentGrid.add(removeFood, 2, gridIndex);

        GridPane.setMargin(nameInput, one);
        GridPane.setMargin(weightInput, two);
        GridPane.setMargin(removeFood, three);
        gridIndex++;

        //insert item into settings
        updateRunBtn("Invalid food items", Settings.addFoodItem(newFood));
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
