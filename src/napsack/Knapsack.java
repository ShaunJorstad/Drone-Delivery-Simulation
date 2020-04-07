package napsack;

import cli.PlacedOrder;

import java.util.ArrayList;

public class Knapsack {

    //should be orders not meals
    private ArrayList<PlacedOrder> droneList; //what is on the drone
    private ArrayList<PlacedOrder> packingList; //what is ordered currently
    private ArrayList<PlacedOrder> nextList; // what is going on next shipment

    private boolean skippedOrder;
    private double maxWeight;
    private double currentWeight;
    private double skippedTime;


    public Knapsack(ArrayList<PlacedOrder> listOfAllOrders) {
        //copy of all orders
        packingList = (ArrayList<PlacedOrder>) listOfAllOrders.clone();

        //text
        droneList = new ArrayList<>();
        //packingList = new ArrayList<>();
        nextList = new ArrayList<>();
        skippedOrder = false;
        maxWeight = 12*16;
        currentWeight = 0;
        skippedTime = 0;
    }

    public ArrayList<PlacedOrder> packDrone(double elapsedTime) {
        //copy of all orders
        try {
            skippedTime = 0; //Amount of time skipped forward to the next ordered food
            currentWeight = 0; //Amount of packed weight on the drone
            droneList.clear(); //What is on the current drone


            //All the orders have been shipped
            if (packingList.isEmpty() && nextList.isEmpty()) {
                return null;
            }

            //moves orders skipped to shipment
            droneList.addAll(nextList);
            nextList.clear();

            //adds the weight of skipped orders
            for (PlacedOrder placedOrder : droneList) {
                currentWeight += placedOrder.getWeight();
            }

            // adds all orders to the drone
            // and adds up the weight
            int i = 0;
            while(!packingList.isEmpty())  {
                if (packingList.get(0).getOrderedTime() < elapsedTime) { //If it has been ordered
                    //Add the order to the drone
                    PlacedOrder placedOrder = packingList.remove(0);
                    currentWeight += placedOrder.getWeight();
                    droneList.add(placedOrder);
                } else {
                    if (i == 0) {
                        //Skip forward in time and the order to the drone
                        skippedTime = packingList.get(0).getOrderedTime() - elapsedTime;
                        PlacedOrder placedOrder = packingList.remove(0);
                        currentWeight += placedOrder.getWeight();
                        droneList.add(placedOrder);
                    } else {
                        break;
                    }
                }
                i++;

            }


            //removes the largest of the non skipped orders until the drone has a correct amount of weight
            while (currentWeight > maxWeight) {
                double BiggestWeight = 0;
                int pos = 0;
                for (int j = droneList.size()-1; j >= 0; j--) {
                    //find the biggest weight, but while trying to load orders that have been skipped over
                    if (droneList.get(j).getWeight()/((droneList.get(j).getTimesSkipped() + 1) << 4)   > BiggestWeight) {
                        BiggestWeight = droneList.get(j).getWeight()/((droneList.get(j).getTimesSkipped()+1) << 4);
                        pos = j;
                    }
                }
                //remove the order and add it to the next list
                currentWeight -= droneList.get(pos).getWeight();
                droneList.get(pos).incrementTimeSkipped();
                nextList.add(droneList.remove(pos));

            }


        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }

        return droneList;
    }

    public double getTimeSkipped() {
        return skippedTime;
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
