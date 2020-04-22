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

    OrderGenerator orderGenerator;

    private static Settings settings; //stores the settings
    int MINUTES_IN_SIM = 240; //The number of minutes in the simulation
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
        orderGenerator = new OrderGenerator(this);
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
        //System.out.println("Total number of orders: " + allOrders.size());
        Results results = new Results(); //Save the results
        TSPResult tspResult;
        ArrayList<Destination> deliveryOrder;

        //Initialize the knapsack and FIFO algorithms
        Knapsack n = new Knapsack(allOrders);
        Fifo f = new Fifo(allOrders);

        int loadMealTime = 0; //In case, the loadMealTime gets adjusted
        double elapsedTime = 2.5; //how far into the simulation are we
        boolean ordersStillToProcess = true; //If there are orders still to process
        double droneSpeed = 25 * 5280 / 60; //Flight speed of the drone in ft/minute
        int droneDeliveryNumber = 1; //Keeps track of what delivery it is
        PlacedOrder currentOrder;

        try {


            //Knapsack
            while (ordersStillToProcess && droneDeliveryNumber < 100) {
                ArrayList<PlacedOrder> droneRun = n.packDrone(elapsedTime); //Get what is on the current drone
                if (droneRun == null) { //Finished delivering orders
                    ordersStillToProcess = false;
                } else {
                    elapsedTime += n.getTimeSkipped() + loadMealTime; //Calculate the current time

                    //Find how long the delivery takes
                    tspResult = TSP(droneRun);
                    deliveryOrder = tspResult.getDeliveryOrder();

                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {
                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed) + .5;
                        results.processSingleDelivery(elapsedTime, currentOrder);

                    }

                    //Return home
                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;

                    //System.out.println("Time that delivery " + droneDeliveryNumber+ " arrived with " + droneRun.size() + " deliveries: " + elapsedTime);

                    //Turnaround time
                    elapsedTime += 2.5;
                }
                droneDeliveryNumber++;
            }
            results.getFinalResults("Knapsack");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        aggregatedResultsKnapsack.add(results); //store the results

        try {
            //Reset the variables for FIFO
            results = new Results();
            elapsedTime = 2.5;
            ordersStillToProcess = true;
            droneDeliveryNumber = 1;

            //FIFO
            while (ordersStillToProcess) {
                ArrayList<PlacedOrder> droneRun = f.packDrone(elapsedTime); //Get what is on the current drone

                if (droneRun == null) { //No more orders to be delivered
                    ordersStillToProcess = false;
                } else {
                    elapsedTime += f.getTimeSkipped() + loadMealTime; //Calculate the current time

                    //Find how long the delivery takes
                    tspResult = TSP(droneRun);
                    deliveryOrder = tspResult.getDeliveryOrder();

                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {

                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed) + .5;
                        results.processSingleDelivery(elapsedTime, currentOrder);

                    }
                    //System.out.println(elapsedTime);

                    //Return home
                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;

                    //System.out.println("Time that delivery " + droneDeliveryNumber+ " arrived with " + droneRun.size() + " deliveries: " + elapsedTime);

                    //Turnaround time
                    elapsedTime += 2.5;
                }
                droneDeliveryNumber++;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        results.getFinalResults("FIFO");
        aggregatedResultsFIFO.add(results); //Store the results

        simRan = true;

    }





    /**
     * Calculates the least cost distance to complete the delivery cycle
     *
     * @param orders ArrayList of placed orders to travel and deliver food to
     * @return The least cost distance to complete the delivery cycle
     */
    public TSPResult TSP(ArrayList<PlacedOrder> orders) {
        ArrayList<Destination> locations = new ArrayList<>(); //The destination of each order

        //The place where the drone leaves and returns to
        Destination home = new Destination("Home", 0, 0, 0);


        //Seed the location ArrayList with each destination from the order list
        for (int i = 0; i < orders.size(); i++) {
            locations.add(orders.get(i).getDest());
        }

        //Call the recursive function which does the brunt work of the algorithm
        return recursiveTSP(locations, home);
    }

    /**
     * Does the brunt work of the TSP. Should run in O(n^2 * 2^n) which is much better than O(n!)
     *
     * @param locations The locations yet to be visited
     * @param lastDest  The last destination the algorithm visited
     * @return The least cost distance to visit all the locations and return
     */
    private TSPResult recursiveTSP(ArrayList<Destination> locations, Destination lastDest) {
        TSPResult finalResult = new TSPResult();
        if (locations.size() == 0) { //base case
            return new TSPResult(lastDest.getDist()); //return to home
        } else {
            double min = Double.MAX_VALUE; //minimum travel distance for given depth in the recursion tree

            //For each possible set of locations
            for (int d = 0; d < locations.size(); d++) {
                Destination newDest = locations.remove(0); //remove the destination

                double distanceBetween = lastDest.distanceBetween(newDest);
                TSPResult newResult = recursiveTSP(locations, newDest);

                newResult.addDistance(distanceBetween);
                //recurse: it finds the fastest route in the subset and then adds the distance between the current
                //          point and the subset. It then takes the minimum at the level so to help in the recursion in
                //          the level above it
                if (newResult.getTotalDistance() < min) {
                    min = newResult.getTotalDistance();
                    Destination temp = new Destination(newDest);
                    temp.setDistToTravelTo(distanceBetween);
                    newResult.addStop(temp);
                    finalResult = newResult;


                }

                //Add back the location because each iteration of the loop on the same level should have the same
                //number of locations to check
                locations.add(newDest);
            }
            //return the shortest distance found in the given level
            return finalResult;
        }

    }


    public static ArrayList<Results> getAggregatedResultsFIFO() {
        return aggregatedResultsFIFO;
    }

    public static ArrayList<Results> getAggregatedResultsKnapsack() {
        return aggregatedResultsKnapsack;
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

    private static PlacedOrder findOrderOnDrone(ArrayList<PlacedOrder> drone, Destination destination) {
        for (int i = 0; i < drone.size(); i++) {
            if (drone.get(i).getDest().equals(destination)) {
                return drone.remove(i);
            }
        }
        System.out.println(drone.get(0).getDest().getDestName());
        System.out.println("ERROR in findOrderOnDrone");
        return null;
    }

}
