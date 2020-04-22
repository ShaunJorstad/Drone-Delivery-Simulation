package cli;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulation.Settings;
import simulation.Results;
import java.util.ArrayList;


public class SimController {

    private static OrderGenerator orderGenerator;
    private static TSP tsp;
    private static AlgorithmRunner algorithmRunner;
    private static FinalResults finalResults;
    private static Settings settings; //stores the settings

    private static ArrayList<Results> aggregatedResultsFIFO; //The results from all 50 simulations
    private static ArrayList<Results> aggregatedResultsKnapsack;
    int NUMBER_OF_SIMULATIONS = 50; //The number of simulations
    public static boolean simInProgress = false; //If the simulation is in progress
    public static boolean simRan; //If a simulation has ran
    static final FileChooser fileChooser = new FileChooser(); //Used for choosing a file
    private static SimulationThread simThread; //The thread that runs the simulation
    private static Button btn; //Button associated with running the simulation


    //pointer to the single instance of SimController
    private static SimController single_instance = null;


    private SimController() {
        //Results for the two algorithms
        aggregatedResultsFIFO = new ArrayList<>();
        aggregatedResultsKnapsack = new ArrayList<>();
        simRan = false; //The simulation hasn't been run yet
        settings = settings.getInstance(); //Get the settings class
        orderGenerator = new OrderGenerator();
        tsp = new TSP();
        algorithmRunner = new AlgorithmRunner(this);
        finalResults = new FinalResults(this);
    }

    //Singleton creator
    public static SimController getInstance() {
        if (single_instance == null) {
            single_instance = new SimController();
        }
        return single_instance;
    }

    /**
     * Generates the orders for the four hour simulation
     * The orders are stored in Orders.xml
     */
    public void generateOrders() {
        orderGenerator.generateOrders();
    }

    /**
     * Run the FIFO and Knapsack algorithms
     */
    public void runAlgorithms() {
        ArrayList<PlacedOrder> allOrders = orderGenerator.getXMLOrders(); //All the xml orders placed
        algorithmRunner.runAlgorithms(allOrders);
        simRan = true;

    }


    public static void addAggregatedResultsFIFO(Results results) {
        SimController.aggregatedResultsFIFO.add(results);
    }

    public static void addAggregatedResultsKnapsack(Results results) {
        SimController.aggregatedResultsKnapsack.add(results);
    }

    public static ArrayList<Results> getAggregatedResultsFIFO() {
        return aggregatedResultsFIFO;
    }

    public static ArrayList<Results> getAggregatedResultsKnapsack() {
        return aggregatedResultsKnapsack;
    }

    public static TSP getTSP() {
        return tsp;
    }


    public int getNUMBER_OF_SIMULATIONS() {
        return NUMBER_OF_SIMULATIONS;
    }

    /**
     * Calculate the average time among all the runs
     * @param aggregatedResults ArrayList of Results
     * @return the average time
     */
    public static double getAggregatedAvgTime(ArrayList<Results> aggregatedResults) {
        return finalResults.getAggregatedAvgTime(aggregatedResults);
    }

    /**
     * Calculate the worst time among all the runs
     * @param aggregatedResults ArrayList of Results
     * @return The worst time
     */
    public static double getAggregatedWorstTime(ArrayList<Results> aggregatedResults) {
        return finalResults.getAggregatedWorstTime(aggregatedResults);
    }


    public boolean hasResults() {
        return simRan;
    }

    public static boolean exportResults(Stage stage) {
        return finalResults.exportResults(stage);
    }

    public static void setCurrentButton(Button button) {
        btn = button;
    }

    public static Button getCurrentButton() {
        return btn;
    }





}
