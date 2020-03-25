package cli;

import java.util.ArrayList;

public class Settings {
    private ArrayList<Integer> orderDistribution;

    public Settings() {
        orderDistribution = new ArrayList<>();
        loadDefaultOrderDist();
    }

    private void loadDefaultOrderDist() {
        orderDistribution.clear();
        orderDistribution.add(15);
        orderDistribution.add(40);
        orderDistribution.add(107);
        orderDistribution.add(13);
    }

    public ArrayList<Integer> getOrderDistribution() {
        return orderDistribution;
    }
}
