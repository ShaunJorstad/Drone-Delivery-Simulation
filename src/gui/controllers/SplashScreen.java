package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class SplashScreen implements Initializable {

    @FXML
    public ImageView SplashImage;
    public Button simButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("assets/drone/drawable-xxxhdpi/drone.png");
        Image image = new Image(file.toURI().toString());
        SplashImage.setImage(image);

        //registers onclick handler
        simButton.setOnAction(this::runSimulationHandler);
    }

    @FXML
    private void runSimulationHandler(ActionEvent event)
    {
        System.out.println("You clicked me!");
//        setTextValue("button clicked");
    }

}
