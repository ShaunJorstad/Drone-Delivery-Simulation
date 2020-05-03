package cli;

import menu.Destination;
import menu.Meal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * An order which has been placed
 */
public class PlacedOrder {
    int orderedTime; //The time in minutes since the start of the simulation that the order was placed
    String clientName; //name of the client
    Meal meal; //The meal that was ordered
    Destination dest; //The destination of the delivery
    int timesSkipped = 0; //How many times the order has been skipped in the Knapsack algorithm

    /**
     * Constructor
     *
     * @param timePlaced The time in minutes since the start of the simulation that the order was placed
     * @param dest       The destination of the delivery
     * @param name       name of the client
     * @param meal       The meal that was ordered
     */
    PlacedOrder(int timePlaced, Destination dest, String name, Meal meal) {
        orderedTime = timePlaced;
        clientName = name;
        this.meal = meal;
        this.dest = dest;
        timesSkipped = 0;
    }

    /**
     * Add the order to the Orders.xml file
     *
     * @param file The file which the orders is being stored in
     */
    public void addToXML(File file) {
        String output = ""; //The output to the file
        try {
            //Append to the file
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            //Add the order to the file
            output += "<order>";
            output += "<cname>" + clientName + "</cname>";
            output += "<ordTime>" + orderedTime + "</ordTime>";
            output += "<dest>" + dest + "</dest>";
            output += "<meal>" + meal + "</meal>";
            output += "</order>";
            printWriter.println(output);

            //Clean up
            printWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return the destination that the order is being delivered to
     * @return
     */
    public Destination getDest() {
        return dest;
    }

    /**
     * Returns the meal that was ordered
     * @return
     */
    public Meal getMeal() {
        return meal;
    }

    /**
     * Get the time that the order was placed
     * @return
     */
    public int getOrderedTime() {
        return orderedTime;
    }

    /**
     * The number of times that the order has been skipped over (Used in the Knapsack algorithm)
     * @return
     */
    public int getTimesSkipped() {return timesSkipped;}

    /**
     * Increases the time that the order has been skipped over by one (Used in the Knapsack algorithm)
     */
    public void incrementTimeSkipped() {
        this.timesSkipped++;
    }

    /**
     * Get the weight of the meal
     * @return
     */
    public float getWeight() {
        return meal.getWeight();
    }

    @Override
    public String toString() {
        return "PlacedOrder{" +
                "orderedTime=" + orderedTime +
                ", clientName='" + clientName + '\'' +
                ", meal=" + meal +
                ", dest=" + dest +
                '}';
    }
}
