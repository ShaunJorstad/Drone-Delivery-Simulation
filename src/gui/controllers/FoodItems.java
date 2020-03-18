package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    double SCALE = 1.6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-underline:true");
        foodItems.setStyle("-fx-underline:true");
    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Splash.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/FoodItems.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Results.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }
    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/FoodItems.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/MealItems.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/OrderDistribution.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Map.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Map.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Drone.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public int scale(int initial) {
        return (int) (initial * SCALE);
    }
}
