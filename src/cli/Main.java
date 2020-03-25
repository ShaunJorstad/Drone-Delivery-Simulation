package cli;

public class Main {
    public static void main(String[] args) {

        System.out.println("testing");
        SimController simController = SimController.getInstance();
        simController.generateOrders();
        simController.runAlgorithms();
    }
}
