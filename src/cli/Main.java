package cli;

public class Main {
    public static void main(String[] args) {

        System.out.println("testing");
        SimController simController = SimController.getInstance();
        for (int i = 0; i < simController.getNUMBER_OF_SIMULATIONS(); i++) {
            simController.generateOrders();
            simController.runAlgorithms();

        }

        //GUIFunction(simController.getAggregatedResultsFIFO(), simController.getAggregatedResultsKnapsack());

    }
}
