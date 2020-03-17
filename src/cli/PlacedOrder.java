package cli;

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
    //Destination dest

    /**
     * Constructor
     * @param timePlaced The time in minutes since the start of the simulation that the order was placed
     * @param name name of the client
     * @param meal The meal that was ordered
     */
    PlacedOrder(int timePlaced /*, Destination dest*/, String name, Meal meal) {
        orderedTime = timePlaced;
        clientName = name;
        this.meal = meal;
        //this.dest = dest;
    }

    /**
     * Add the order to the Orders.xml file
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
            //Dest
            output += "<meal>" + meal + "</meal>";
            output += "</order>";
            printWriter.println(output);

            //Clean up
            printWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
