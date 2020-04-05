package cli;

import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgressThread implements Runnable{
    Button simBtn;

    public ProgressThread(Button btn) {
        simBtn = btn;
    }

    @Override
    public void run() {
        System.out.println("Entered progress thread");
        long length= 5000;
        int status = SimController.getSimStatus();
        while (status < 50) {
            status = SimController.getSimStatus();
            if (status == -1) {
                break;
            }
            simBtn.setText("% " + Integer.toString(status));
            try {
                wait(length);
            } catch (InterruptedException e) {
                System.out.println("Progress thread wait broken");
                e.printStackTrace();
            }
        }
        if (status == 50) {
            simBtn.setText("view results");
            simBtn.setOnAction(actionEvent -> {
                Navigation.pushScene("Splash");
                try {
                    Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
                    Navigation.inflateScene(root, "FoodItems", (Stage) Navigation.getCurrentScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
