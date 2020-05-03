package cli;

import javafx.concurrent.Task;

public class SimulationThread extends Task<Void> {

    @Override
    protected Void call() {
        SimController.simInProgress = true;

        SimController simController = SimController.getInstance();
        for (int i = 0; i < simController.getNUMBER_OF_SIMULATIONS(); i++) {

            updateProgress(i, simController.getNUMBER_OF_SIMULATIONS());
            simController.generateOrders();
            simController.runAlgorithms();
        }
        return null;
    }
}
