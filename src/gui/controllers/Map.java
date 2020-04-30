/**
 * Cyan Team
 * Author: Shaun Jorstad
 * <p>
 * controller for the Map FXML document
 */

package gui.controllers;

import cli.Coordinate;
import cli.SimController;
import cli.SimulationThread;
import gui.Navigation;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulation.Settings;
import menu.Destination;

import static java.nio.file.StandardCopyOption.*;

public class Map implements Initializable {
    SimulationThread statusThread;
    ArrayList<Coordinate>  mapPoints;
    boolean isFirst;
    boolean textboxIsUP;
	
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
    public Button newMapButton;
    public ImageView uploadImage;
    public ImageView downloadImage;
    public VBox settingButtons;
    public ScrollPane scrollpane;
    public GridPane contentGrid;
    public VBox runBtnVbox;
    public Pane pointPane;
    

    double scale;
    Coordinate homeCoordinate;
    TextField distanceTextField;
    TextField textField;
    
    String currentMap;

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
        Navigation.updateRunBtn(runSimButton, Settings.verifySettings());

        Insets runInsets = new Insets(0,0,120,0);
        VBox.setMargin(runSimButton, runInsets);
        
        injectCursorStates();
        
        currentMap = "default.png";
        File map = new File("assets/mapImages/" + currentMap);
        Image mapImageFile = new Image(map.toURI().toString());
        mapImage.setImage(mapImageFile);
        

        Insets mapInset = new Insets(20,0,0,50);
        VBox.setMargin(mapImage, mapInset);
        inflateMapPoints();

        SimController.setCurrentButton(runSimButton);
        EventHandler<MouseEvent> mouseEventEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                modifyPoints(e);
            }
        };
        EventHandler<KeyEvent> keyEventEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                processText(event);
            }
        };
        pointPane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEventEventHandler);
        pointPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEventEventHandler);
        mapPoints = new ArrayList<>();
        isFirst = true;
        textboxIsUP = false;
        scale = -1;
        homeCoordinate = new Coordinate(0, 0);
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
        if (SimController.resultsLock) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner((Stage) home.getScene().getWindow());
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("A simulation has to be run\n and finish executing before\n you can navigate to the results page"));
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
            Navigation.inflateScene(root, "Results", (Stage) home.getScene().getWindow());
            Navigation.pushScene("Map");
        }
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
    
    public void handleNewMap(ActionEvent actionEvent) {
    	
        textField = new TextField();
        textField.setPromptText("Map Name: ");
        textField.setOnAction( EventHandler -> {
        	try {
            	continueNewMap(textField.getText());
        	} catch(Exception e) {
        		e.printStackTrace();
        	} finally {
        		((Stage)(textField.getScene().getWindow())).close();
        	}
        });
        
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage) home.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(textField);
        Scene dialogScene = new Scene(dialogVbox, 100, 50);
        dialog.setScene(dialogScene);
        dialog.show();

    }
    
    public void continueNewMap(String name) throws Exception {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Map Image File");
    	fileChooser.getExtensionFilters().addAll(
    	         new ExtensionFilter("Image Files", "*.png", "*.jpg"));
    	File selectedFile = fileChooser.showOpenDialog((Stage)home.getScene().getWindow());

	    File newMap = new File("assets/mapImages/"+selectedFile.getName());
	    newMap.createNewFile();
    	if (selectedFile != null) {
    	    Files.copy(selectedFile.toPath(), newMap.toPath(), REPLACE_EXISTING);

    	    newMap = new File("assets/mapImages/"+selectedFile.getName());
            Image mapImageFile = new Image(newMap.toURI().toString());
            mapImage.setImage(mapImageFile);
    	}
    	
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
            SimController.clearResults();
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

    /**
     * Modify points on the map
     * @param mouseEvent
     */
    public void modifyPoints(MouseEvent mouseEvent) {
        //If the textbox is up, don't allow the user to add more points
        if (textboxIsUP) {
            return;
        }
        //Location of the mouse clock
        Coordinate coordinate = new Coordinate((int)mouseEvent.getX(), (int)mouseEvent.getY());

        //Make sure the click is in the correct part of the pane
        if (mouseEvent.getY() < 348) {
            if (!mouseEvent.isControlDown()) { //add a point
                Circle circle = new Circle(4);

                distanceTextField = new TextField();

                if (isFirst) { //Home base
                    circle.setFill(Color.WHITESMOKE);
                    coordinate.setFirst(true);
                    isFirst = false;
                    homeCoordinate = coordinate;
                    distanceTextField.setVisible(false);
                } else {
                    circle.setFill(Color.RED);
                    if (scale < 0) {
                        distanceTextField.setPromptText("Distance in ft");
                    } else {
                        distanceTextField.setText("" +Settings.convertGUItoFEET(coordinate.distanceBetween(homeCoordinate), scale));
                    }
                }


                VBox nameVBox = new VBox();
                textField = new TextField();
                textField.setPromptText("Name: ");
                circle.setCenterX(coordinate.getX());
                circle.setCenterY(coordinate.getY());

                nameVBox.setLayoutX(coordinate.getX());
                nameVBox.setLayoutY(coordinate.getY());
                nameVBox.getChildren().addAll(textField, distanceTextField);
                pointPane.getChildren().add(circle);
                pointPane.getChildren().add(nameVBox);
                mapPoints.add(coordinate);
                textboxIsUP = true;
                System.out.println(coordinate);

            } else { //Remove a point
                for (int i = 0; i < mapPoints.size(); i++) { //Find the closest
                    if (coordinate.distanceBetween(mapPoints.get(i)) <= 5) {
                        Coordinate removed = mapPoints.remove(i);
                        if (removed.isFirst()) { //Home base was removed
                            isFirst = true;
                        }
                        try {
                            Settings.removeMapPoint(removed);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        pointPane.getChildren().remove(i+1);
                        return;
                    }
                }
            }

        }

    }

    public void processText(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.getKeyCode("Enter"))) {
            if (!textboxIsUP) { //If the textbox is not up, ignore the input
                return;
            }

            //Convert the distance between current destination and homebase and turn it into a scale
            String temp = distanceTextField.getText();
            double distInFeet;
            try {
                distInFeet = Double.parseDouble(temp);
                scale = Settings.calculateScale(distInFeet, mapPoints.get(mapPoints.size()-1).distanceBetween(homeCoordinate));
            } catch (Exception exception) {
                scale = -1;
            }

            String name = textField.getText();
            Coordinate currentDest = mapPoints.get(mapPoints.size()-1);
            try {
                Settings.addMapPoint(name, currentDest.getX(), currentDest.getY());

                pointPane.getChildren().remove(pointPane.getChildren().size()-1);
                textboxIsUP = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }
    }

}
