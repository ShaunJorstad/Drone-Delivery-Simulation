package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layouts/Splash.fxml"));
        primaryStage.setTitle("Cyan: Dromedary Drones");

        Scene splashScene = new Scene(root, Navigation.scale(1000), Navigation.scale(700));
        splashScene.getStylesheets().add("gui/CSS/Splash.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");

        primaryStage.setScene(splashScene);

        primaryStage.show();
    }
}
