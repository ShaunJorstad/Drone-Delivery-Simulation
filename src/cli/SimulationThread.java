package cli;

import gui.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationThread implements Runnable{
    @Override
    public void run() {
//        long length= 5000;
        SimController.simInProgress = true;

        // TODO: run the simulation
        SimController simController = SimController.getInstance();
        for (int i = 0; i < simController.getNUMBER_OF_SIMULATIONS(); i++) {
            simController.generateOrders();
            simController.runAlgorithms();
            SimController.getCurrentButton().setText("sim: " + Integer.toString(i));
        }

        SimController.getCurrentButton().setText("view results");
        SimController.getCurrentButton().setStyle("-fx-background-color: #0078D7");
        SimController.getCurrentButton().setDisable(false);
        SimController.getCurrentButton().setOnAction(actionEvent -> {
            Navigation.pushScene("Splash");
            try {
                Parent root = FXMLLoader.<Parent>load(getClass().getResource("/gui/layouts/FoodItems.fxml"));
                Navigation.inflateScene(root, "FoodItems", (Stage) Navigation.getCurrentScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        SimController.simInProgress = false;
    }
}
