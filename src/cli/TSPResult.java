package cli;

import menu.Destination;

import java.util.ArrayList;

/**
 * The result of a TSP run
 */
public class TSPResult {
    ArrayList<Destination> deliveryOrder; //Stores a list of the delivery order to the destination
    double totalDistance; //The total distance the drone has to travel to make all the deliveries

    /**
     * Default constructor
     */
    public TSPResult() {
        totalDistance = 0;
        deliveryOrder = new ArrayList<>();
    }

    /**
     * Constructor
     * @param totalDistance The total distance the drone has had to travel
     */
    public TSPResult(double totalDistance) {
        this.totalDistance = totalDistance;
        deliveryOrder = new ArrayList<>();
    }

    /**
     * Gets the total distance the drone has had to travel
     * @return
     */
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Gets the delivery order of the drone
     * @return
     */
    public ArrayList<Destination> getDeliveryOrder() {
        return deliveryOrder;
    }

    /**
     * Adds a delivery point to the drone run
     * @param d The new destination that drone is stopping at
     */
    public void addStop(Destination d) {
        deliveryOrder.add(d);
    }

    /**
     * Add the distance to the total distance that the drone has to travel
     * @param dist The additionally distance the drone has to travel
     */
    public void addDistance(double dist) {
        totalDistance += dist;
    }
}
