package cli;

import org.w3c.dom.ranges.Range;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SimController {
    File nameFile;
    ArrayList<String> names = new ArrayList<>();
    File ordersFile;


    public SimController() {
        try {
            nameFile = new File("Names.txt");
            Scanner s = new Scanner(nameFile);
            while(s.hasNext()) {
                names.add(s.next());
            }
            s.close();

        } catch (Exception e) {
            System.out.println((e.getMessage()));
        }
    }

    public void generateOrders() {
        Random random = new Random();
        float rand;
        int minutesInSim = 240;
        int curMin = 0;
        int randName;

        try{
            ordersFile = new File("Orders.xml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        float chanceOfOrderPerM = 0;
        //calculate the chance of order per minute

        while (curMin < minutesInSim) {
            rand = random.nextFloat();

            if (rand < chanceOfOrderPerM) {
                randName = random.nextInt(names.size());
                PlacedOrder ord = new PlacedOrder(curMin, names.get(randName));
                ord.addToXML(ordersFile);

            } else {
                curMin++;
            }
        }




    }





}
