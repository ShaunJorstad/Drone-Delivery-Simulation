package menu;

public class Drone {

    private double weight;
    private double speed;
    private double maxFlightTime;
    private double turnaroundTime;
    private double deliveryTime;

    public Drone(double weight, double speed, double MFT, double turnaroundTime, double deliveryTime){
        this.weight = weight;
        this.speed = speed;
        this.maxFlightTime = MFT;
        this.turnaroundTime = turnaroundTime;
        this.deliveryTime = deliveryTime;
    }

    public double getWeight() {
        return weight;
    }

    public double getSpeed() {
        return speed;
    }

    public double getMaxFlightTime() {
        return maxFlightTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setMaxFlightTime(double maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
