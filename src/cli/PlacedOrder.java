package cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PlacedOrder {
    int orderedTime;
    String clientName;
    //Order order
    //Destination dest


    PlacedOrder(int timePlaced /*, Destination dest*/, String name) {
        orderedTime = timePlaced;
        clientName = name;

        //this.dest = dest;
    }

    public void addToXML(File file) {
        String output = "";
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            output += "<order>";
            output += "<cname>" + clientName + "</cname>";
            output += "<ordTime>" + orderedTime + "</ordTime>";
            //Dest
            //Order
            output += "</order>";
            printWriter.println(output);
            printWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
