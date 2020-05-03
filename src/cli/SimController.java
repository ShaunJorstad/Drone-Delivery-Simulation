package cli;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulation.Settings;
import simulation.Results;
import java.util.ArrayList;


public class SimController {

    private static OrderGenerator orderGenerator; //Generates the orders
    private static TSP tsp; //Runs the TSP algorithm
    private static AlgorithmRunner algorithmRunner; //Runs the simulation
    private static FinalResults finalResults; //Gets the final results of the simulation
    private static Settings settings; //stores the settings

    private static ArrayList<Results> aggregatedResultsFIFO; //The results from all 50 simulations
    private static ArrayList<Results> aggregatedResultsKnapsack;

    int NUMBER_OF_SIMULATIONS = 50; //The number of simulations
    public static boolean simInProgress = false; //If the simulation is in progress
    public static boolean simRan; //If a simulation has ran
    public static boolean resultsLock = true; //If the results page is locked
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
        resultsLock = false;
    }

    /**
     * Clear the results on the results page
     */
    public static void clearResults() {
        if (!resultsLock) {
            aggregatedResultsKnapsack.clear();
            aggregatedResultsFIFO.clear();
        }
    }

    /**
     * Add the result from a single 4 hour run of FIFO to the aggregated results
     * @param results
     */
    public static void addAggregatedResultsFIFO(Results results) {
        SimController.aggregatedResultsFIFO.add(results);
    }

    /**
     * Add the results from a single 4 hour run of Knapsack to the aggregated results
     * @param results
     */
    public static void addAggregatedResultsKnapsack(Results results) {
        SimController.aggregatedResultsKnapsack.add(results);
    }

    /**
     * Get the aggregated results for FIFO
     * @return
     */
    public static ArrayList<Results> getAggregatedResultsFIFO() {
        return aggregatedResultsFIFO;
    }

    /**
     * Get the aggregated results for Knapsack
     * @return
     */
    public static ArrayList<Results> getAggregatedResultsKnapsack() {
        return aggregatedResultsKnapsack;
    }

    /**
     * Get a reference to the TSP algorithm
     * @return
     */
    public static TSP getTSP() {
        return tsp;
    }

    /**
     * Get the number of simulation to be run
     * @return
     */
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

    /**
     * Export the simulation results
     * @param stage
     * @return
     */
    public static boolean exportResults(Stage stage) {
        return finalResults.exportResults(stage);
    }

    /**
     * Set the run button
     * @param button
     */
    public static void setCurrentButton(Button button) {
        btn = button;
    }

    /**
     * Return the run button
     * @return
     */
    public static Button getCurrentButton() {
        return btn;
    }





}
