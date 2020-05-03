package menu;


public class Drone {

    private double weight; //Max carrying capacity
    private double speed; //The max flight speed of the drone
    private double maxFlightTime; //The maximum flight time of the drone
    private double turnaroundTime; //The time it takes from when the drone gets back to when it can fly again
    private double deliveryTime; //The time it takes to drop off a delivery
    private double elapsedTime; //How far into the simulation the drone is

    /**
     * Constructor
     * @param weight Max carrying capacity
     * @param speed The max flight speed of the drone
     * @param MFT The maximum flight time of the drone
     * @param turnaroundTime The time it takes from when the drone gets back to when it can fly again
     * @param deliveryTime The time it takes to drop off a delivery
     */
    public Drone(double weight, double speed, double MFT, double turnaroundTime, double deliveryTime) {
        this.weight = weight;
        this.speed = speed;
        this.maxFlightTime = MFT;
        this.turnaroundTime = turnaroundTime;
        this.deliveryTime = deliveryTime;
        this.elapsedTime = 0;
    }

    /**
     * Copy constructor
     * @param other
     */
    public Drone(Drone other) {
        this.weight = other.weight;
        this.speed = other.speed;
        this.maxFlightTime = other.maxFlightTime;
        this.turnaroundTime = other.turnaroundTime;
        this.deliveryTime = other.deliveryTime;
        this.elapsedTime = 0;
    }


    /**
     * Get the max carrying capacity of the drone
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Get the max speed of the drone
     * @return
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the max flight time for the drone
     * @return
     */
    public double getMaxFlightTime() {
        return maxFlightTime;
    }

    /**
     * Get the turnaround time for the drone
     * @return
     */
    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    /**
     * Get the delivery time for the drone
     * @return
     */
    public double getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Get the elapsed time for the drone
     * @return
     */
    public double getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Set the max carrying capacity for the drone
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Set the max speed for the drone
     * @param speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Set the max flight time for the drone
     * @param maxFlightTime
     */
    public void setMaxFlightTime(double maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    /**
     * Set the turnaround time for the drone
     * @param turnaroundTime
     */
    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    /**
     * Set the delivery time for the drone
     * @param deliveryTime
     */
    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    /**
     * Se the elapsed time for the drone
     * @param elapsedTime
     */
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
