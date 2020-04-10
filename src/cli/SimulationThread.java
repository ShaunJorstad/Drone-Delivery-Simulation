package cli;

import gui.Navigation;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationThread extends Task<Void> {

    @Override
    protected Void call() throws Exception {
        SimController.simInProgress = true;
//        ProgressBar progressBar;
//        HBox hBox;
//        Label label = new Label("Sim Progress: ");
//        ProgressIndicator progressIndicator;


        // TODO: run the simulation
        SimController simController = SimController.getInstance();
        for (int i = 0; i < simController.getNUMBER_OF_SIMULATIONS(); i++) {
            updateProgress(i, simController.getNUMBER_OF_SIMULATIONS());
            simController.generateOrders();
            simController.runAlgorithms();
        }
        /*
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

         */
        return null;
    }
}
