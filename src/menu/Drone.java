package menu;

public class Drone {

    private double weight;
    private double speed;
    private double maxFlightTime;
    private double turnaroundTime;
    private double deliveryTime;
    private double elapsedTime;

    public Drone(double weight, double speed, double MFT, double turnaroundTime, double deliveryTime){
        this.weight = weight;
        this.speed = speed;
        this.maxFlightTime = MFT;
        this.turnaroundTime = turnaroundTime;
        this.deliveryTime = deliveryTime;
        this.elapsedTime = 0;
    }

    public Drone(Drone other) {
        this.weight = other.weight;
        this.speed = other.speed;
        this.maxFlightTime = other.maxFlightTime;
        this.turnaroundTime = other.turnaroundTime;
        this.deliveryTime = other.deliveryTime;
        this.elapsedTime = 0;
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

    public double getElapsedTime() {
        return elapsedTime;
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

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "weight=" + weight +
                ", speed=" + speed +
                ", maxFlightTime=" + maxFlightTime +
                ", turnaroundTime=" + turnaroundTime +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
