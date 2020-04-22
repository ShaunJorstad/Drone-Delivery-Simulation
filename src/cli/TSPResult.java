package cli;

import menu.Destination;

import java.util.ArrayList;

public class TSPResult {
    ArrayList<Destination> deliveryOrder;
    double totalDistance;

    public TSPResult() {
        totalDistance = 0;
        deliveryOrder = new ArrayList<>();
    }

    public TSPResult(double totalDistance) {
        this.totalDistance = totalDistance;
        deliveryOrder = new ArrayList<>();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public ArrayList<Destination> getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void addStop(Destination d) {
        deliveryOrder.add(d);
    }

    public void addDistance(double dist) {
        totalDistance += dist;
    }
}
