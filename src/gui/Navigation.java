/**
 * Cyan Team
 * Author: Shaun Jorstad
 * This singleton class is is used to create a functional back button.
 * <p>
 * Member variables
 * -----------------
 * - instance       Maintains a single instance to the class. This allows the controllers to call .getInstance() on the singleton object
 * - sceneStack     Stack of Scenes the user navigates through. When back arrow is pressed the last scene is popped off and navigated too
 * - scenes         List of Scenes in the app. used for error checking
 * - SCALE          scaling factor of the UI. accomodates screens of different sizes and densities.
 */

package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class Navigation {
    private static Navigation instance = null;
    public static ArrayList<String> sceneStack;
    private static ArrayList<String> scenes;
    private static Scene currentScene;

    private static double SCALE = 1.6;

    private Navigation() {
        sceneStack = new ArrayList<>();
        scenes = new ArrayList<>();
        String[] SCENES = {"Splash", "Results", "FoodItems", "MealItems", "OrderDistribution", "Map", "Drone"};
        scenes.addAll(Arrays.asList(SCENES));
    }

    static void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public static Scene getCurrentScene() {return currentScene;}

    /**
     * pushes scene onto the scene stack
     *
     * @param scene name of scene
     */
    public static void pushScene(String scene) {
        if (instance == null) {
            instance = new Navigation();
            System.out.println("constructing navigation");
        }
        if (scenes.contains(scene)) {
            sceneStack.add(scene);
        }
    }

    /**
     * Pops the last scene off of the stack
     *
     * @return name of last scene or null
     */
    public static String popScene() {
        if (sceneStack.isEmpty()) {
            return null;
        }
        return sceneStack.remove(sceneStack.size() - 1);
    }

    /**
     * Checks if scene stack is empty
     * Will be used when loading the image of the backbutton
     *
     * @return true if empty, false otherwise
     */
    public static boolean isEmpty() {
        if (instance == null) {
            instance = new Navigation();
        }
        return sceneStack.isEmpty();
    }

    /**
     * Scales the input value based upon the scale factor
     *
     * @param initial value to be scaled
     * @return scaled value
     */
    public static int scale(int initial) {
        return (int) (initial * SCALE);
    }

    /**
     * Scales the input value based upon the scale factor
     *
     * @param initial value to be scaled
     * @return scaled value
     */
    public static double scale(double initial) {
        return initial * SCALE;
    }

    /**
     * Inflates a scene (navigating to a new screen)
     *
     * @param root      fxml document being inflated
     * @param nextScene name of the scene being inflated
     * @param stage     current stage of the application
     */
    public static void inflateScene(Parent root, String nextScene, Stage stage) {
        Scene splashScene = new Scene(root, 800, 600);
        splashScene.getStylesheets().add("gui/CSS/" + nextScene + ".css");
        splashScene.getStylesheets().add("gui/CSS/Settings.css");
        splashScene.getStylesheets().add("gui/CSS/Navigation.css");
        splashScene.setCursor(Cursor.HAND);
        stage.setScene(splashScene);
        currentScene = splashScene;
    }

    /**
     * Inflates a scene (navigating to a new screen)
     *
     * if there is invalid input (indicated by the invalidFields arrayList a popup asks the user if they want to confirm navigation and clear all invalid input
     *
     * @param root      fxml document being inflated
     * @param nextScene name of the scene being inflated
     * @param stage     current stage of the application
     * @param invalidFields arraylist of invalid fields the use rhas modified
     */
    public static void inflateScene(Parent root, String nextScene, Stage stage, ArrayList invalidFields) {
        if (!invalidFields.isEmpty()) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(stage);
            VBox root_cntr = new VBox(20);
            Text alert_txt = new Text("You have entered invalid input (denoted by red boxes) \nmoving to another screen will revert these fields to their prior input\n\nDo you want to continue?");
            alert_txt.setTextAlignment(TextAlignment.CENTER);
            root_cntr.getChildren().add(alert_txt);
            root_cntr.setAlignment(Pos.CENTER);
            Scene dialogScene = new Scene(root_cntr, 500, 200);
            HBox buttons_cntr = new HBox(20);
            buttons_cntr.setAlignment(Pos.CENTER);
            Button cancel_btn = new Button("cancel");
            cancel_btn.setOnMouseClicked((event) -> {
                dialog.close();
            });

            Button continue_btn = new Button("continue");
            continue_btn.setOnMouseClicked((event) -> {
                Scene splashScene = new Scene(root, 800, 600);
                splashScene.getStylesheets().add("gui/CSS/" + nextScene + ".css");
                splashScene.getStylesheets().add("gui/CSS/Settings.css");
                splashScene.getStylesheets().add("gui/CSS/Navigation.css");
                splashScene.setCursor(Cursor.HAND);
                stage.setScene(splashScene);
                currentScene = splashScene;
                dialog.close();
            });

            buttons_cntr.getChildren().addAll(cancel_btn, continue_btn);
            root_cntr.getChildren().add(buttons_cntr);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            Scene splashScene = new Scene(root, 800, 600);
            splashScene.getStylesheets().add("gui/CSS/" + nextScene + ".css");
            splashScene.getStylesheets().add("gui/CSS/Settings.css");
            splashScene.getStylesheets().add("gui/CSS/Navigation.css");
            splashScene.setCursor(Cursor.HAND);
            stage.setScene(splashScene);
            currentScene = splashScene;
        }
    }

    /**
     * returns instance of this singleton class. Constructs one if it hasn't been constructed yet.
     *
     * @return singleton instance
     */
    public static Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }

    public static void updateRunBtn(Button runButton, String errorMessage) {
        if (errorMessage.equals("")) {
            runButton.setStyle("-fx-background-color: #0078D7");
            runButton.setText("Run");
            runButton.setDisable(false);
        } else {
            runButton.setStyle("-fx-background-color: #EC2F08");
            runButton.setText(errorMessage);
            runButton.setDisable(true);
        }
    }

    public static void updateRunBtn(Button runButton, String errorMessage, ArrayList invalidFields) {
        if (!invalidFields.isEmpty()) {
            runButton.setStyle("-fx-background-color: #EC2F08");
            runButton.setText("Invalid fields");
            runButton.setDisable(true);
        } else if (!errorMessage.equals("")) {
            runButton.setStyle("-fx-background-color: #EC2F08");
            runButton.setText(errorMessage);
            runButton.setDisable(true);
        } else {
            runButton.setStyle("-fx-background-color: #0078D7");
            runButton.setText("Run");
            runButton.setDisable(false);
        }
    }
}
