package cli;

import menu.Destination;
import menu.Drone;
import napsack.Knapsack;
import simulation.Fifo;
import simulation.Results;
import simulation.Settings;

import java.util.ArrayList;

public class AlgorithmRunner {
    TSP tsp;
    SimController simController;

    public AlgorithmRunner(SimController simController) {
        this.simController = simController;
        this.tsp = simController.getTSP();
    }

    public void runAlgorithms(ArrayList<PlacedOrder> allOrders) {
        Drone drone = Settings.getDrone();
        //System.out.println("Total number of orders: " + allOrders.size());
        Results results = new Results(); //Save the results
        TSPResult tspResult;
        ArrayList<Destination> deliveryOrder;

        //Initialize the knapsack and FIFO algorithms
        Knapsack n = new Knapsack(allOrders);
        Fifo f = new Fifo(allOrders);

        int loadMealTime = 0; //In case, the loadMealTime gets adjusted
        double elapsedTime = drone.getTurnaroundTime(); //how far into the simulation are we
        boolean ordersStillToProcess = true; //If there are orders still to process
        double droneSpeed = drone.getSpeed() * 5280 / 60; //Flight speed of the drone in ft/minute
        int droneDeliveryNumber = 1; //Keeps track of what delivery it is
        PlacedOrder currentOrder;

        try {

            //Knapsack
            while (ordersStillToProcess) {
                ArrayList<PlacedOrder> droneRun = n.packDrone(elapsedTime); //Get what is on the current drone
                if (droneRun == null) { //Finished delivering orders
                    ordersStillToProcess = false;
                } else {
                    elapsedTime += n.getTimeSkipped() + loadMealTime; //Calculate the current time

                    //Find how long the delivery takes
                    tspResult = tsp.runTSP(droneRun);
                    deliveryOrder = tspResult.getDeliveryOrder();


                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;
                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {
                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        elapsedTime += drone.getDeliveryTime();
                        results.processSingleDelivery(elapsedTime, currentOrder);
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed);

                    }




                    //System.out.println("Time that delivery " + droneDeliveryNumber+ " arrived with " + droneRun.size() + " deliveries: " + elapsedTime);

                    //Turnaround time
                    elapsedTime += drone.getTurnaroundTime();
                }
                droneDeliveryNumber++;
            }
            results.getFinalResults("Knapsack");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        SimController.addAggregatedResultsKnapsack(results); //store the results

        try {
            //Reset the variables for FIFO
            results = new Results();
            elapsedTime = drone.getTurnaroundTime();
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
                    tspResult = tsp.runTSP(droneRun);
                    deliveryOrder = tspResult.getDeliveryOrder();

                    elapsedTime += deliveryOrder.get(0).getDist()/droneSpeed;

                    //Deliver the orders and process the results
                    for (int i = 0; i < deliveryOrder.size(); i++) {
                        elapsedTime += drone.getDeliveryTime();
                        currentOrder = findOrderOnDrone(droneRun, deliveryOrder.get(i));
                        results.processSingleDelivery(elapsedTime, currentOrder);
                        elapsedTime += (deliveryOrder.get(i).getDistToTravelTo() / droneSpeed);

                    }
                    //System.out.println(elapsedTime);


                    //System.out.println("Time that delivery " + droneDeliveryNumber+ " arrived with " + droneRun.size() + " deliveries: " + elapsedTime);

                    //Turnaround time
                    elapsedTime += drone.getTurnaroundTime();
                }
                droneDeliveryNumber++;

            }
            results.getFinalResults("FIFO");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        simController.addAggregatedResultsFIFO(results); //Store the results
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

}
