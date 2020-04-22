package cli;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menu.Destination;
import menu.Meal;
import napsack.Knapsack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulation.Fifo;
import simulation.Settings;
import simulation.Results;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimController {

    private static OrderGenerator orderGenerator;
    private static TSP tsp;
    private static AlgorithmRunner algorithmRunner;
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
        double sum = 0;
        for (int i = 0; i < aggregatedResults.size(); i++) {
            sum += aggregatedResults.get(i).getAvgTime();
        }
        return sum / aggregatedResults.size();
    }

    /**
     * Calculate the worst time among all the runs
     * @param aggregatedResults ArrayList of Results
     * @return The worst time
     */
    public static double getAggregatedWorstTime(ArrayList<Results> aggregatedResults) {
        double worst = Double.MIN_VALUE;
        for (int i = 0; i < aggregatedResults.size(); i++) {
            if (aggregatedResults.get(i).getWorstTime() > worst) {
                worst = aggregatedResults.get(i).getWorstTime();
            }

        }
        return worst;
    }

    public static String exportResults(ArrayList<Results> resultsFifo, ArrayList<Results> resultsKnapsack) {
        String out = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" +
                "<data-set>\n\t" + "<record>\n\t\t" + 
                "<Simulation_Number>1</Simulation_Number>\n\t\t" +
                "<Fifo_Average_Time>6.663998369015784</Fifo_Average_Time>\n\t\t" +
                "<Fifo_Worst_Time>12.402527638089623</Fifo_Worst_Time>\n\t\t" +
                "<Knapsack_Average_Time>5.6679585828477075</Knapsack_Average_Time>\n\t\t" +
                "<Knapsack_Worst_Time>12.12398691955238</Knapsack_Worst_Time>\n\t" +
                "</record>";
        for (int i = 0; i < resultsFifo.size(); i++) {
            out += "\n\t<record>\n\t\t";
            out += "<Simulation_Number>" + (i + 1) + "</Simulation_Number>\n\t\t";
            out += "<Fifo_Average_Time>" + resultsFifo.get(i).getAvgTime() + "</Fifo_Average_Time>\n\t\t";
            out += "<Fifo_Worst_Time>" + resultsFifo.get(i).getWorstTime() + "</Fifo_Worst_Time>\n\t\t";
            out += "<Knapsack_Average_Time>" + resultsKnapsack.get(i).getAvgTime() + "</Knapsack_Average_Time>\n\t\t";
            out += "<Knapsack_Worst_Time>" + resultsKnapsack.get(i).getWorstTime() + "</Knapsack_Worst_Time>\n\t";
            out += "</record>";
        }
        out += "\n</data-set>";
        return out;
    }

    public boolean hasResults() {
        return simRan;
    }

    public static boolean exportResults(Stage stage) {
        fileChooser.setTitle("Export Settings");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            //TODO: pipe this string into the file
            try {
                FileWriter fw = new FileWriter(file, false);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(exportResults(getAggregatedResultsFIFO(), getAggregatedResultsKnapsack()));

                fw.close();
                pw.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public static void setCurrentButton(Button button) {
        btn = button;
    }

    public static Button getCurrentButton() {
        return btn;
    }

    public static void runSimulations() {
        simThread = new SimulationThread();
        simThread.run();
    }



}
