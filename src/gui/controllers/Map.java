/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the Map FXML document
 */

package gui.controllers;

import cli.SimController;
import cli.SimulationThread;
import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulation.Settings;
import menu.Destination;

public class Map implements Initializable {
    SimulationThread statusThread;
	
	@FXML
    public ImageView mapImage;
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
    public Button back;
    public ImageView backImage;
    public Button runSimButton;
    //public Button importMapButton;
    //public Button exportMapButton;
    public ImageView uploadImage;
    public ImageView downloadImage;
    public VBox settingButtons;
    public ScrollPane scrollpane;
    public GridPane contentGrid;
    public VBox runBtnVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        map.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        
        
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

        loadIcons();
        
        Insets runInsets = new Insets(0,0,120,0);
        VBox.setMargin(runSimButton, runInsets);
        
        injectCursorStates();
        
        File map = new File("assets/mapImage.png");
        Image mapImageFile = new Image(map.toURI().toString());
        mapImage.setImage(mapImageFile);
        

        Insets mapInset = new Insets(20,0,0,50);
        VBox.setMargin(mapImage, mapInset);
        inflateMapPoints();
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

        //uploadImage.setImage(new Image(new File("assets/icons/upload.png").toURI().toString()));
        //downloadImage.setImage(new Image(new File("assets/icons/download.png").toURI().toString()));
    }

    public void injectCursorStates() {

        List<Button> items = Arrays.asList(home, settings, results, back, runSimButton, foodItems, mealItems, orderDistribution, map, drone);//, importMapButton, exportMapButton);
        for (Button item : items) {
            item.setOnMouseEntered(mouseEvent -> {
                item.getScene().setCursor(Cursor.HAND);
            });
            item.setOnMouseExited(mouseEvent -> {
                item.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }
    

    public void handleNavigateHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Splash.fxml"));
        Navigation.inflateScene(root, "Splash", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "FoodItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root, "MealItems", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "OrderDistribution", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {

    }

    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Drone", (Stage) home.getScene().getWindow());
        Navigation.pushScene("Map");
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.popScene();
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.inflateScene(root, lastScene, (Stage) home.getScene().getWindow());
    }
    
    public void handleImportMap(ActionEvent actionEvent) {
    }

    public void handleExportMap(ActionEvent actionEvent) {
    }

    public void handleRunSimulation(ActionEvent actionEvent) {
        ProgressBar pb = new ProgressBar();
        try {
            if (SimController.simRan) {
                Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
                Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
                Navigation.pushScene("Map");
                return;
            }
            SimulationThread simulationThread = new SimulationThread();
            simulationThread.setOnRunning((successEvent) -> {
                runSimButton.setStyle("-fx-background-color: #1F232F");
                runSimButton.setText("running simulation");
                runSimButton.setDisable(true);

                pb.progressProperty().bind(simulationThread.progressProperty());
                runBtnVbox.getChildren().add(pb);
            });

            simulationThread.setOnSucceeded((successEvent) -> {
                SimController.getCurrentButton().setText("view results");
                SimController.getCurrentButton().setStyle("-fx-background-color: #0078D7");
                SimController.getCurrentButton().setDisable(false);
                SimController.simInProgress = false;
                runBtnVbox.getChildren().remove(pb);
            });

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(simulationThread);
            executorService.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void inflateMapPoints() {
    	int gridInd = 1;
		for (Destination curr : Settings.getMap()) {
			
			//Name of destination
    		Text destName = new Text(curr.getDestName());
    		destName.getStyleClass().add("foodName");
            /*destName.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    update simcontroller destinations
                }
            });*/
            Text xCoord = new Text("" + curr.getX());
    		xCoord.getStyleClass().add("foodName");
            /*xCoord.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    update simcontroller destinations
                }
            });*/
            Text yCoord = new Text("" + curr.getY());
    		yCoord.getStyleClass().add("foodName");
            /*yCoord.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    update simcontroller destinations
                }
            });*/
            
            
            contentGrid.add(destName, 0, gridInd);
            contentGrid.add(xCoord, 1, gridInd);
            contentGrid.add(yCoord, 2, gridInd);
            gridInd++;
            
            
    	}
    }
}
