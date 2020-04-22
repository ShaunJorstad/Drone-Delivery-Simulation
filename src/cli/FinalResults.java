package cli;

import javafx.stage.Stage;
import simulation.Results;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static cli.SimController.*;

public class FinalResults {
    SimController simController;

    public FinalResults(SimController simController) {
        this.simController = simController;
    }

    public boolean exportResults(Stage stage) {
        fileChooser.setTitle("Export Settings");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                FileWriter fw = new FileWriter(file, false);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(exportResults(getAggregatedResultsFIFO(), getAggregatedResultsKnapsack()));

                fw.close();
                pw.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    private String exportResults(ArrayList<Results> resultsFifo, ArrayList<Results> resultsKnapsack) {
        String out = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" +
                "<data-set>\n\t" + "<record>\n\t\t" +
                "<Simulation_Number>1</Simulation_Number>\n\t\t" +
                "<Fifo_Average_Time>6.663998369015784</Fifo_Average_Time>\n\t\t" +
                "<Fifo_Worst_Time>12.402527638089623</Fifo_Worst_Time>\n\t\t" +
                "<Knapsack_Average_Time>5.6679585828477075</Knapsack_Average_Time>\n\t\t" +
                "<Knapsack_Worst_Time>12.12398691955238</Knapsack_Worst_Time>\n\t" +
                "</record>";
        for (int i = 0; i < resultsFifo.size(); i++) {
            out += "\n\t<record>\n\t\t";
            out += "<Simulation_Number>" + (i + 1) + "</Simulation_Number>\n\t\t";
            out += "<Fifo_Average_Time>" + resultsFifo.get(i).getAvgTime() + "</Fifo_Average_Time>\n\t\t";
            out += "<Fifo_Worst_Time>" + resultsFifo.get(i).getWorstTime() + "</Fifo_Worst_Time>\n\t\t";
            out += "<Knapsack_Average_Time>" + resultsKnapsack.get(i).getAvgTime() + "</Knapsack_Average_Time>\n\t\t";
            out += "<Knapsack_Worst_Time>" + resultsKnapsack.get(i).getWorstTime() + "</Knapsack_Worst_Time>\n\t";
            out += "</record>";
        }
        out += "\n</data-set>";
        return out;
    }

    /**
     * Calculate the average time among all the runs
     * @param aggregatedResults ArrayList of Results
     * @return the average time
     */
    public double getAggregatedAvgTime(ArrayList<Results> aggregatedResults) {
        double sum = 0;
        for (int i = 0; i < aggregatedResults.size(); i++) {
            sum += aggregatedResults.get(i).getAvgTime();
        }
        return sum / aggregatedResults.size();
    }

    /**
     * Calculate the worst time among all the runs
     * @param aggregatedResults ArrayList of Results
     * @return The worst time
     */
    public double getAggregatedWorstTime(ArrayList<Results> aggregatedResults) {
        double worst = Double.MIN_VALUE;
        for (int i = 0; i < aggregatedResults.size(); i++) {
            if (aggregatedResults.get(i).getWorstTime() > worst) {
                worst = aggregatedResults.get(i).getWorstTime();
            }

        }
        return worst;
    }

}
