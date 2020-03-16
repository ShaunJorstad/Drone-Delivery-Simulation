package gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Results implements Initializable {

    public Button exportButton;
    public Button results;
    public Button home;
    public Button settings;
    public VBox vBox;
    public Label resultsTitle;
    public HBox navBar;

    double SCALE = 1.6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results.setStyle("-fx-underline:true");


    }

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Splash.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleExport(ActionEvent actionEvent) {
        System.out.println("Exporting");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/FoodItems.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        Stage currentStage = (Stage) home.getScene().getWindow();
        currentStage.setScene(splashScene);
    }

    public void handleNavigateResults(ActionEvent actionEvent) {
        System.out.println("already in settings");
    }

    public int scale(int initial) {
        return (int) (initial * SCALE);
    }
}
