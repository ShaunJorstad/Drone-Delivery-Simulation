/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the Splash FXML document
 */

package gui.controllers;

import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Splash implements Initializable {

    @FXML
    public ImageView SplashImage;
    public Button simButton;
    public VBox vBox;
    public HBox navBar;
    public Button home;
    public Button settings;
    public Button results;
    public Button back;
    public ImageView backImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File droneFile = new File("assets/drone/drawable-xxxhdpi/drone.png");
        Image droneImage = new Image(droneFile.toURI().toString());
        SplashImage.setImage(droneImage);

        File backFile = new File("assets/backArrow.png");
        Image backArrowImage = new Image(backFile.toURI().toString());
        backImage.setImage(backArrowImage);

        home.setStyle("-fx-underline:true");

        //registers onclick handler
        simButton.setOnAction(this::runSimulationHandler);
    }

    @FXML
    public void runSimulationHandler(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    public void handleNavigateHome(ActionEvent actionEvent) {
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Splash");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Splash");
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }
}
