package cli;

import menu.Destination;
import menu.Drone;
import simulation.Knapsack;
import simulation.Fifo;
import simulation.Results;
import simulation.Settings;
import java.util.ArrayList;


/**
 * Runs the simulation
 */
public class AlgorithmRunner {
    TSP tsp; //The TSP algorithm
    SimController simController; //Reference to the simController

    /**
     * Constructor
     * @param simController
     */
    public AlgorithmRunner(SimController simController) {
        this.simController = simController;
        this.tsp = simController.getTSP();
    }

    /**
     * Runs one 4 hour run of the simulation
     * @param allOrders The orders list to be processed for the 4 hour simulation
     */
    public void runAlgorithms(ArrayList<PlacedOrder> allOrders) {
        ArrayList<Drone> fleet = new ArrayList<>(); //Fleet of drone to be used
        int fleetSize = Settings.getDroneFleetSize(); //Size of the drone fleet

        Drone drone = Settings.getDrone(); //Get the characteristics of a drone

        for (int i = 0; i < fleetSize; i++) { //Create an appropriately sized fleet
            fleet.add(new Drone(drone));
        }

        Results results = new Results(); //Save the results
        TSPResult tspResult; //The result of the TSP algorithm
        ArrayList<Destination> deliveryOrder; //The order in which the deliveries were made

        //Initialize the knapsack and FIFO algorithms
        Knapsack n = new Knapsack(allOrders, Settings.calcMaxDeliveries());
        Fifo f = new Fifo(allOrders, Settings.calcMaxDeliveries());

        int loadMealTime = 0; //In case, the loadMealTime gets adjusted
        double elapsedTime = drone.getTurnaroundTime(); //how far into the simulation are we
        boolean ordersStillToProcess = true; //If there are orders still to process
        double droneSpeed = drone.getSpeed() * 5280 / 60; //Flight speed of the drone in ft/minute
        PlacedOrder currentOrder; //Current order being processed

        try {

            //Knapsack
            while (ordersStillToProcess) {
                drone = FindAvailableDrone(fleet); //Find an available drone
                elapsedTime = drone.getElapsedTime(); //Set the elapsed time to where the particular drone is at
                ArrayList<PlacedOrder> droneRun = n.packDrone(elapsedTime); //Get what is on the current drone

                if (droneRun == null) { //Finished delivering orders
                    ordersStillToProcess = false;
                } else {
                    elapsedTime += n.getTimeSkipped() + loadMealTime; //Calculate the current time

                    //Find how long the delivery takes
                    tspResult = tsp.runTSP(droneRun);

                    deliveryOrder = tspResult.getDeliveryOrder();

                    //Increment the time by how long it takes to get to the first destination
                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;

                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {
                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        elapsedTime += drone.getDeliveryTime();
                        results.processSingleDelivery(elapsedTime, currentOrder);
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed);
                    }

                    //Turnaround time
                    elapsedTime += drone.getTurnaroundTime();

                    //Store the elapsed time for the drone
                    drone.setElapsedTime(elapsedTime);
                }
            }
            results.getFinalResults("Knapsack");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ResetElapsedTime(fleet);
        SimController.addAggregatedResultsKnapsack(results); //store the results

        try {
            //Reset the variables for FIFO
            results = new Results();
            ordersStillToProcess = true;

            //FIFO
            while (ordersStillToProcess) {
                drone = FindAvailableDrone(fleet); //Find an available drone
                elapsedTime = drone.getElapsedTime(); //Get the elapsed time for the particular drone
                ArrayList<PlacedOrder> droneRun = f.packDrone(elapsedTime); //Get what is on the current drone

                if (droneRun == null) { //No more orders to be delivered
                    ordersStillToProcess = false;
                } else {
                    elapsedTime += f.getTimeSkipped() + loadMealTime; //Calculate the current time

                    //Find how long the delivery takes
                    tspResult = tsp.runTSP(droneRun);

                    deliveryOrder = tspResult.getDeliveryOrder();

                    //Increment the time by how long it takes to get to the first destination
                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;

                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {
                        elapsedTime += drone.getDeliveryTime();
                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        results.processSingleDelivery(elapsedTime, currentOrder);
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed);

                    }

                    //Turnaround time
                    elapsedTime += drone.getTurnaroundTime();

                    drone.setElapsedTime(elapsedTime);
                }

            }
            results.getFinalResults("FIFO");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ResetElapsedTime(fleet);
        simController.addAggregatedResultsFIFO(results); //Store the results
        SimController.resultsLock = false;
    }

    /**
     * Based on the destination, it finds the appropriate order to deliver
     * @param ordersOnDrone The arrayList containing the orders on the drone
     * @param destination The destination the food is being delivered to
     * @return The order to be delivered at the destination
     */
    private static PlacedOrder findOrderOnDrone(ArrayList<PlacedOrder> ordersOnDrone, Destination destination) {
        for (int i = 0; i < ordersOnDrone.size(); i++) {
            if (ordersOnDrone.get(i).getDest().equals(destination)) {
                return ordersOnDrone.remove(i);
            }
        }
        System.out.println(ordersOnDrone.get(0).getDest().getDestName());
        System.out.println("ERROR in findOrderOnDrone");
        return null;
    }

    /**
     * Finds the drone with the lowest elapsed time
     * @param fleet The fleet of drones that you are operating with
     * @return
     */
    private Drone FindAvailableDrone(ArrayList<Drone> fleet) {
        double minTime = Double.MAX_VALUE;
        Drone drone = fleet.get(0);
        for (int i = 0; i < fleet.size(); i++) {
            if (fleet.get(i).getElapsedTime() < minTime) {
                drone = fleet.get(i);
                minTime = drone.getElapsedTime();
            }
        }
        return drone;
    }

    /**
     * Reset the elapsed time for the entire fleet
     * @param fleet The fleet that you are working with
     */
    private void ResetElapsedTime(ArrayList<Drone> fleet) {
        for (int i = 0; i < fleet.size(); i++) {
            fleet.get(i).setElapsedTime(0);
        }
    }

}
