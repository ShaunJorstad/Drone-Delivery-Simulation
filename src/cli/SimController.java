package cli;

import menu.DefaultFood;
import menu.Meal;
import org.w3c.dom.ranges.Range;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SimController {
    File nameFile; //File that contains a list of names
    ArrayList<String> names; //Stores the list of names
    File ordersFile; //xml file that saves the orders
    DefaultFood defaultFood; //The object that stores the default meal combos

    //pointer to the single instance of SimController
    private static SimController single_instance = null;


    private SimController() {
        names = new ArrayList<>();
        try {
            nameFile = new File("Names.txt"); //open the names file

            //Seed the arraylist with names in the file
            Scanner s = new Scanner(nameFile);
            while(s.hasNext()) {
                names.add(s.next());
            }
            s.close();

        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }

        defaultFood = new DefaultFood(); //Get the default food settings
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
        Random random = new Random(); //new random generator

        int minutesInSim = 240; //The number of minutes in the simulation
        int curMin = 0; //The current minute the simulation is in
        int randName; //random integer for what name to choose the ArrayList
        Meal m; //Current meal being ordered

        try{
            ordersFile = new File("Orders.xml"); //open the orders file
            FileWriter fileWriter = new FileWriter(ordersFile, false); //clear out the orders file
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("<listOfOrders>");
            printWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        double chanceOfOrderPerM = .25; //Set to .25 as default
        //calculate the chance of order per minute

        //For each minute in the simulation
        while (curMin < minutesInSim) {

            if (random.nextDouble() < chanceOfOrderPerM) { //If the probability is low enough, generate an order
                //Get a random name
                randName = random.nextInt(names.size());

                //Get a random meal based on the distribution
                m = randomMeal(random.nextDouble());

                if (m != null) {
                    //Create a new order
                    PlacedOrder ord = new PlacedOrder(curMin, names.get(randName), m);
                    //Add the order to the xml file
                    ord.addToXML(ordersFile);
                } else {
                    System.out.println("randomMeal is broken\n");
                }//m!=null

            } else { //If an order is not generated
                curMin++;
            }

        }
        try{
            ordersFile = new File("Orders.xml"); //open the orders file
            FileWriter fileWriter = new FileWriter(ordersFile, true); //clear out the orders file
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("</listOfOrders>");
            printWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Returns a random meal based on the distribution
     * @param rand A random double between 0 and 1
     * @return The meal that was randomly selected
     */
    private Meal randomMeal(double rand) {
        double counter = 0; //Keeps track of the distribution through the loop

        //Store the list of meals
        List<Meal> lm = defaultFood.getMeals();

        //Iterate to where the random number points to in the distribution
        for (int i = 0; i < lm.size(); i++) {
            counter += lm.get(i).getDistribution();
            if (rand < counter) {
                return lm.get(i); //return the meal selected
            }
        }

        //If it returns null the distribution invalid
        return null;
    }





}
