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
import javafx.stage.StageStyle;
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
    

    ArrayList invalidFields;

    TextField distanceTextField;
    TextField textField;
    Coordinate oldHome;
    Double oldScale;
    
    String currentMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settings.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");
        map.setStyle("-fx-border-color: #0078D7;" + "-fx-border-width: 0 0 5px 0;");

        invalidFields = new ArrayList();
        
        
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

        Insets runInsets = new Insets(100,0,0,0);
        VBox.setMargin(runSimButton, runInsets);
        
        injectCursorStates();
        
        File map = new File("assets/mapImages/" + Settings.getMapImage());
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
        initializeDynamicPoints();
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

        uploadImage.setImage(new Image(new File("assets/icons/upload.png").toURI().toString()));
        downloadImage.setImage(new Image(new File("assets/icons/download.png").toURI().toString()));
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
        Navigation.inflateScene(root, "Map", "Splash", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void HandleNavigateSettings(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root,"Map", "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateResults(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Results.fxml"));
        Navigation.inflateScene(root,"Map", "Results", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateFoodItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
        Navigation.inflateScene(root, "Map", "FoodItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMealItems(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/MealItems.fxml"));
        Navigation.inflateScene(root,"Map", "MealItems", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateOrderDistribution(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/OrderDistribution.fxml"));
        Navigation.inflateScene(root, "Map", "OrderDistribution", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateMap(ActionEvent actionEvent) throws IOException {

    }


    public void handleNavigateDrone(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/Drone.fxml"));
        Navigation.inflateScene(root, "Map", "Drone", (Stage) home.getScene().getWindow(), invalidFields);
    }

    public void handleNavigateBack(ActionEvent actionEvent) throws IOException {
        String lastScene = Navigation.peekScene(); // TODO: this needs to be put back on the stack if user hits cancel,
        if (lastScene == null)
            return;
        String path = "/gui/layouts/" + lastScene + ".fxml";
        Parent root = FXMLLoader.<Parent>load(getClass().getResource(path));
        Navigation.navigateBack(root, lastScene, (Stage) home.getScene().getWindow(), invalidFields);
    }
    
    public void handleImportMap(ActionEvent actionEvent) {
    }
    
    

    public void handleExportMap(ActionEvent actionEvent) {
    }
    
    public void handleNewMap(ActionEvent actionEvent) {
    	
        textField = new TextField();
        //textField.setPromptText("Map Name: ");
        textField.setOnAction( EventHandler -> {
        	try {
            	continueNewMap(textField.getText());
        	} catch(Exception e) {
        		e.printStackTrace();
        	} finally {
        		((Stage)(textField.getScene().getWindow())).close();
        	}
        });
        
        Text text = new Text("Name your new map:");
        //text.setStyle(value);
        Insets textInsets = new Insets(10,10,10,10);
        Insets otherInsets = new Insets(10,20,10,20);
        
        final Stage dialog = new Stage(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage) home.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        VBox.setMargin(text, textInsets);
        VBox.setMargin(textField, otherInsets);
        dialogVbox.getChildren().addAll(text, textField);
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
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
    	    Settings.setMapImage(selectedFile.getName());

    	    newMap = new File("assets/mapImages/"+selectedFile.getName());
            Image mapImageFile = new Image(newMap.toURI().toString());
            mapImage.setPreserveRatio(false);
            mapImage.setFitWidth(500);
            mapImage.setFitHeight(350);
            mapImage.setImage(mapImageFile);
            
            Settings.removeAllMapPoints();
            int max = pointPane.getChildren().size()-1;
            for (int i = 0; i < max; i++)
            	pointPane.getChildren().remove(1);
            updateMapPoints();
            isFirst = true;
            Settings.setScale(-1);
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
                    Settings.setHomeGUILoc(coordinate);
                    distanceTextField.setVisible(false);

                    changeDestCoordinates(0);

                } else {
                    circle.setFill(Color.RED);
                    if (Settings.getScale() < 0) { //Get the distance from the user
                        distanceTextField.setPromptText("Distance in ft");
                    } else {
                        //Display the distance
                        distanceTextField.setText("" +
                                Settings.convertGUItoFEET(coordinate.distanceBetween(Settings.getHomeGUILoc()),
                                Settings.getScale()));
                    }
                }


                VBox nameVBox = new VBox();

                //Name text field
                textField = new TextField();
                textField.setPromptText("Name: ");

                //Put the circle and the vbox where the user clicked
                circle.setCenterX(coordinate.getX());
                circle.setCenterY(coordinate.getY());
                nameVBox.setLayoutX(coordinate.getX());
                nameVBox.setLayoutY(coordinate.getY());

                nameVBox.getChildren().addAll(textField, distanceTextField);
                pointPane.getChildren().add(circle);
                pointPane.getChildren().add(nameVBox);

                //Add the GUI coordinate to list that stores all the map points
                mapPoints.add(coordinate);

                textboxIsUP = true;

            } else { //Remove a point
                for (int i = 0; i < mapPoints.size(); i++) { //Find the closest point
                    if (coordinate.distanceBetween(mapPoints.get(i)) <= 5) {
                        Coordinate removed = mapPoints.remove(i);
                        if (removed.isFirst()) { //Home base was removed
                            isFirst = true;
                            oldHome = new Coordinate(removed);
                        }
                        try {
                            //Convert the GUI point to the destination point in feet
                            removed = removed.subtract(Settings.getHomeGUILoc());
                            removed = Settings.convertGUItoFEET(removed, Settings.getScale());
                            //Remove the point from the stored destination list
                            Settings.removeMapPoint(removed);
                            updateMapPoints();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        //Remove the point from the displayed map
                        pointPane.getChildren().remove(i+1);
                        return;
                    }
                }
            }

        }

    }
    
    
    private void updateMapPoints() {
    	contentGrid.getChildren().clear();
    	inflateMapPoints();
    }

    /**
     * User confirms the details about the destination map
     * @param keyEvent
     */
    public void processText(KeyEvent keyEvent) {

        if (keyEvent.getCode().equals(KeyCode.getKeyCode("Enter"))) {
            if (!textboxIsUP) { //If the textbox is not up, ignore the input
                return;
            }

            //Convert the distance between current destination and homebase and turn it into a scale
            String temp = distanceTextField.getText();
            oldScale = Settings.getScale();
            double distInFeet;
            try {
                distInFeet = Double.parseDouble(temp);
                Settings.setScale(Settings.calculateScale(distInFeet,
                        mapPoints.get(mapPoints.size()-1).distanceBetween(Settings.getHomeGUILoc())));
                if (Math.abs(oldScale-Settings.getScale()) > .02) {
                    changeDestCoordinates(1);
                }
            } catch (Exception exception) {
                //Settings.setScale(-1);
            }

            //Get the name of the destination
            String name = textField.getText();

            //Get the GUI coordinates of the new point
            Coordinate currentDest = mapPoints.get(mapPoints.size()-1);

            //Convert into the destination coordinates
            currentDest = currentDest.subtract(Settings.getHomeGUILoc());
            currentDest = Settings.convertGUItoFEET(currentDest, Settings.getScale());

            try {
                //Add the map to the saved settings
                Settings.addMapPoint(name, (int)currentDest.getX(), (int)currentDest.getY(), (Stage) home.getScene().getWindow());
                updateMapPoints();

                //Clear the text fields
                pointPane.getChildren().remove(pointPane.getChildren().size()-1);
                textboxIsUP = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }
    }

    /**
     * When the user enters the map settings page, add all of the dynamic points to the map
     */
    public void initializeDynamicPoints() {
        ArrayList<Destination> map = Settings.getMap(); //Destination locations in feet
        Coordinate home = new Coordinate(0, 0);


        for (int d = map.size()-1; d >= 0; d--) { //For each destination, add the point
            Circle circle = new Circle(4);

            Coordinate destCords = map.get(d).getCoordinates(); //Destination location measured in feet


            //Convert the destination in feet to the GUI location
            Coordinate GUICoordinate = Settings.convertFEETtoGUI(destCords, Settings.getScale());
            GUICoordinate.add(Settings.getHomeGUILoc());

            if (destCords.distanceBetween(home) < 1) { //If it is the home location
                circle.setFill(Color.WHITESMOKE);
                GUICoordinate.setFirst(true);
                isFirst = false;
            } else {
                circle.setFill(Color.RED);
            }
            //Center the circle on the GUI coordinates
            circle.setCenterX(GUICoordinate.getX());
            circle.setCenterY(GUICoordinate.getY());

            //Add the points to the map and to the list of points
            pointPane.getChildren().add(circle);
            mapPoints.add(GUICoordinate);
        }
    }

    public void changeDestCoordinates(int change) {
        ArrayList<Destination> map = Settings.getMap(); //Destination locations in feet
        //Coordinate home = new Coordinate(0, 0);


        for (int d = map.size()-1; d >= 0; d--) { //For each destination, add the point

            Destination destination = map.get(d); //Destination location measured in feet
            Coordinate destCords = new Coordinate();

            //Convert the destination in feet to the GUI location
            if (change == 0) {
                Coordinate GUICoordinate = Settings.convertFEETtoGUI(destination.getCoordinates(), Settings.getScale());
                GUICoordinate.add(oldHome);
                GUICoordinate = GUICoordinate.subtract(Settings.getHomeGUILoc());
                destCords = Settings.convertGUItoFEET(GUICoordinate, Settings.getScale());
            } else if (change == 1) {
                Coordinate GUICoordinate = Settings.convertFEETtoGUI(destination.getCoordinates(), oldScale);
                destCords = Settings.convertGUItoFEET(GUICoordinate, Settings.getScale());
            }


            destination.setCoordinates(destCords);
            map.set(d, destination);


        }
        updateMapPoints();
    }

}
