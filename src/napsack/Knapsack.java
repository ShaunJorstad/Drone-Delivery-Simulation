package napsack;

import cli.PlacedOrder;
import menu.DefaultFood;
import menu.Meal;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Knapsack {

    //should be orders not meals
    public ArrayList<PlacedOrder> droneList; //what is on the drone
    public ArrayList<PlacedOrder> packingList; //what is ordered currently
    public ArrayList<PlacedOrder> nextList; // what is going on next shipment

    private boolean skippedOrder;
    private double maxWeight;
    private double currentWeight;


    public Knapsack(){

        //text
        droneList = new ArrayList<>();
        //packingList = orders for the hour;
        nextList = new ArrayList<>();
        skippedOrder=false;
        maxWeight=12;
        currentWeight=0;
    }

    public void packDrone(){
        //copy of all orders
        ArrayList<PlacedOrder> packingClone = (ArrayList<PlacedOrder>) packingList.clone();

        //moves orders skipped to shippment
        droneList.addAll(nextList);
        nextList.clear();

        //adds the weight of skipped orders
        for(PlacedOrder m:droneList){
            currentWeight+=m.getMeal().getWeight();
        }

        // adds all orders to the drone
        // and adds up the wight
        int i;
        for (i = 0; i < packingClone.size(); i++) {
            droneList.add(packingClone.get(i));
            currentWeight += packingClone.get(i).getMeal().getWeight();
            packingClone.remove(i);
        }

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
        }
    }

}
