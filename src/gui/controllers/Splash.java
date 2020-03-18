package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

    double SCALE = 1.6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("assets/drone/drawable-xxxhdpi/drone.png");
        Image image = new Image(file.toURI().toString());
        SplashImage.setImage(image);

        home.setStyle("-fx-underline:true");

        //registers onclick handler
        simButton.setOnAction(this::runSimulationHandler);
    }

    @FXML
    private void runSimulationHandler(ActionEvent event)
    {
        System.out.println("You clicked me!");

        //
        //        Text text = new Text("testing");
//        TextFlow newText = new TextFlow();
//        newText.getChildren().add(text);
//
//        vBox.getChildren().add(newText);
    }

    public void handleNavigateHome(ActionEvent actionEvent) {
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

    public int scale(int initial) {
        return (int) (initial * SCALE);
    }
}
