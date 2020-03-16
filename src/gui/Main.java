package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Double SCALE = 1.6;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layouts/Splash.fxml"));
        primaryStage.setTitle("Cyan: Dromedary Drones");

        Scene splashScene = new Scene(root, scale(1000), scale(700));
        splashScene.getStylesheets().add("gui/CSS/Splash.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");


        primaryStage.setScene(splashScene);

        // --- TESTING
//        Parent root = FXMLLoader.load(getClass().getResource("layouts/Results.fxml"));
//        primaryStage.setTitle("Cyan: Dromedary Drones");
//
//        Scene splashScene = new Scene(root, scale(1000), scale(700));
//        splashScene.getStylesheets().add("gui/CSS/Results.css");
//        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
//
//        primaryStage.setScene(splashScene);

        primaryStage.show();
    }

    public int scale(int initial) {
        return (int) (initial * SCALE);
    }
}
