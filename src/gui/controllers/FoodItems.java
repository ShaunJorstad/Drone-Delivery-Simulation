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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.FoodItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        foodItems.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        loadIcons();
        injectCursorStates();

        gridIndex = 1;
        inflateFoodItems();
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

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(141, 153, 250));
        shadow.setOffsetX(0);
        shadow.setOffsetY(10);
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setSpread(.01);
        runSimButton.setEffect(shadow);
    }

    public void injectCursorStates() {
        List<Button> items = Arrays.asList(home, settings, results, back, runSimButton, foodItems, mealItems, orderDistribution, map, drone, importSettingsButton, exportSettingsButton, addFood);
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
    }

    public void handleExportSettings(ActionEvent actionEvent) {
    }

    public void handleRunSimulation(ActionEvent actionEvent) {
    }

    public void inflateFoodItems() {
//        load food items from settings object
        for (FoodItem item : SimController.getDefaultFood().getFoods()) {
            TextField nameInput = new TextField(item.getName());
            nameInput.getStyleClass().add("foodName");
            nameInput.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    update simcontroller data
//                    verify weights
                }
            });

            TextField weightInput = new TextField(Float.toString(item.getWeight()));
            weightInput.getStyleClass().add("foodWeight");
            weightInput.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // update simcontroller data
//                verify weights
                }
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
            removeFood.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // remove fields
                    contentGrid.getChildren().remove(nameInput);
                    contentGrid.getChildren().remove(weightInput);
                    contentGrid.getChildren().remove(removeFood);

                    //TODO:  delete from settings
                    /* code */
                }
            });
            injectCursorStates(removeFood);

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
        TextField nameInput = new TextField("food name");
        nameInput.getStyleClass().add("foodName");
        nameInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                    update simcontroller data
//                    verify weights
            }
        });

        TextField weightInput = new TextField("0");
        weightInput.getStyleClass().add("foodWeight");
        weightInput.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // update simcontroller data
//                verify weights
            }
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

            // TODO: delete from settings
            /* code */
        });
        injectCursorStates(removeFood);

        contentGrid.add(nameInput, 0, gridIndex);
        contentGrid.add(weightInput, 1, gridIndex);
        contentGrid.add(removeFood, 2, gridIndex);

        GridPane.setMargin(nameInput, one);
        GridPane.setMargin(weightInput, two);
        GridPane.setMargin(removeFood, three);
        gridIndex++;

        //insert
    }

    public void modifyRunButton(String text, boolean valid) {
        if (valid) {
//            make button blue
            runSimButton.setStyle("-fx-background-color: #0078D7");
        } else {
//            make button red
            runSimButton.setStyle("-fx-background-color: #EC2F08");
        }
        runSimButton.setText(text);
    }
}
