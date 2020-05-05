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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Splash implements Initializable {
    @FXML
    public ImageView SplashImage;
    public VBox vBox;
    public HBox navBar;
    public Button home;
    public Button settings;
    public Button results;
    public Button back;
    public ImageView backImage;
    public Button nextButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File droneFile = new File("assets/drone/drawable-xxxhdpi/drone.png");
        Image droneImage = new Image(droneFile.toURI().toString());
        SplashImage.setImage(droneImage);

        home.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        injectCursorStates();
        loadIcons();
    }

    /**
     * loads the icons for buttons
     */
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

        home.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 3px 0;");

        nextButton.setTooltip(new Tooltip("Navigates to the settings pages. Simulation can be run from there"));

        injectCursorStates();
    }

    /**
     * creates the cursor states for nodes on the scene
     */
    public void injectCursorStates() {
        List<Button> items = Arrays.asList(home, settings, results, back, nextButton);
        for (Button item : items) {
            item.setOnMouseEntered(mouseEvent -> {
                item.getScene().setCursor(Cursor.HAND);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }

    public void handleNavigateHome(ActionEvent actionEvent) {
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"Splash", "FoodItems", (Stage) home.getScene().getWindow(), new ArrayList());
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root,"FoodItems", "Results", (Stage) home.getScene().getWindow(), new ArrayList());
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }

    public void handleNextButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"Splash", "FoodItems", (Stage) home.getScene().getWindow(), new ArrayList());
    }
}
