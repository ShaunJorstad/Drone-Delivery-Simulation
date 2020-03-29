package napsack;

import cli.PlacedOrder;
import menu.DefaultFood;
import menu.Meal;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Knapsack {

    //should be orders not meals
    private ArrayList<PlacedOrder> droneList; //what is on the drone
    private ArrayList<PlacedOrder> packingList; //what is ordered currently
    private ArrayList<PlacedOrder> nextList; // what is going on next shipment

    private boolean skippedOrder;
    private double maxWeight;
    private double currentWeight;
    private double skipepdTime;


    public Knapsack(ArrayList<PlacedOrder> listOfAllOrders) {
        //copy of all orders
        ArrayList<PlacedOrder> packingList = (ArrayList<PlacedOrder>) listOfAllOrders.clone();

        //text
        droneList = new ArrayList<>();
        packingList = new ArrayList<>();
        nextList = new ArrayList<>();
        skippedOrder = false;
        maxWeight = 12;
        currentWeight = 0;
        skipepdTime = 0;
    }

    public void packDrone(double elapsedTime) {
        //copy of all orders
        try {
            ArrayList<PlacedOrder> packingClone = (ArrayList<PlacedOrder>) packingList.clone();


            //moves orders skipped to shippment
            droneList.addAll(nextList);
            nextList.clear();

            //adds the weight of skipped orders
            for (PlacedOrder m : droneList) {
                currentWeight += m.getMeal().getWeight();
            }

            // adds all orders to the drone
            // and adds up the wight
            int i;
            for (i = 0; i < packingList.size(); i++) {
                droneList.add(packingList.get(i));
                currentWeight += packingList.get(i).getMeal().getWeight();
                packingList.remove(i);
            }

            int firstpos = 0;
            //removes the largest of the non skipped orders until the drone has a correct amount of weight
            while (currentWeight > maxWeight) {
                double BiggestWeight = 0;
                int pos = 0;
                for (int j = nextList.size(); j < droneList.size(); j++) {
                    //equal to pick the one later in the queue
                    if (droneList.get(j).getMeal().getWeight() <= BiggestWeight) {
                        BiggestWeight = droneList.get(j).getMeal().getWeight();
                        pos = j;
                    }
                }
                currentWeight -= droneList.get(pos).getMeal().getWeight();
                nextList.add(droneList.remove(pos));
                if (pos == firstpos)
                    firstpos = pos + 1;
            }
            if (elapsedTime < packingClone.get(firstpos).getOrderedTime() && firstpos == 0)
                skipepdTime = packingClone.get(firstpos).getOrderedTime() - elapsedTime;

        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
//        return droneList;
    }

    public double getTimeSkipped() {
        return skipepdTime;
    }

    public ArrayList<PlacedOrder> getDroneList() {
        return droneList;
    }

    public ArrayList<PlacedOrder> getPackingList() {
        return packingList;
    }

    public ArrayList<PlacedOrder> getNextList() {
        return nextList;
    }
}
